package couch.football.service.match;

import couch.football.domain.match.Application;
import couch.football.domain.match.Match;
import couch.football.domain.match.MatchGender;
import couch.football.domain.match.MatchStatus;
import couch.football.domain.member.Member;
import couch.football.domain.stadium.Stadium;
import couch.football.repository.match.MatchRepository;
import couch.football.repository.match.ApplicationRepository;
import couch.football.repository.member.MemberRepository;
import couch.football.repository.stadium.StadiumRepository;
import couch.football.request.match.MatchCreateRequest;
import couch.football.response.match.ApplicationResponse;
import couch.football.response.match.MatchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationServiceTest {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private MatchService matchService;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void clean() {
        applicationRepository.deleteAll();
    }

    //Member, Stadium, Match 생성 -> (add+Entity) 메서드 사용하기
    @Test
    @DisplayName("경기 신청")
    void applyMatch() {
        //given
        Stadium stadium = addStadium("couch", "깔끔", "서울특별시 1234");
        Member member = addMember("minyuk1234", "minyuk", "male", "minyuk@gmail.com");

        Match match = Match.builder()
                .stadium(stadium)
                .request(addMatch(12, "male", "조심", LocalDateTime.now().plusHours(2)))
                .build();
        matchRepository.save(match);

        //when
        ApplicationResponse response = applicationService.applyMatch(match.getId(), member); //경기 신청
        MatchResponse findMatch = matchService.get(match.getId()); //경기 상세 조회

        //then
        assertEquals(1L, applicationRepository.count()); //신청 결과 확인

        assertEquals(11, response.getRest()); // 남은 자리수 확인
        assertEquals("OPEN", response.getStatus()); //경기 상태 = 신청가능

        assertEquals(1, findMatch.getApplicantNum()); //신청자 수 확인
        assertEquals("OPEN", findMatch.getStatus()); //경기 상태 = 신청가능
    }

    @Test
    @DisplayName("경기 신청 불가능 - 성별 불일치")
    void applyMatchX() {
        //given
        Stadium stadium = addStadium("couch", "깔끔", "서울특별시 1234");
        Member member = addMember("minyuk1234", "minyuk", "male", "minyuk@gmail.com");

        Match match = Match.builder()
                .stadium(stadium)
                .request(addMatch(12, "female", "조심", LocalDateTime.now().plusHours(2)))
                .build();
        matchRepository.save(match);

        //expected
        assertThrows(IllegalArgumentException.class, () -> {
            applicationService.applyMatch(match.getId(), member); //"경기 성별을 확인해주세요."
        });
    }

    @Test
    @DisplayName("경기 신청 불가능 - 신청 마감") //성별 all test OK
    void applyMatchClose() {
        //given
        Stadium stadium = addStadium("couch", "깔끔", "서울특별시 1234");
        Member member1 = addMember("minyuk1234", "minyuk", "male", "minyuk@gmail.com");
        Member member2 = addMember("tester1111", "tester", "female", "tester@gmail.com");

        Match match = Match.builder()
                .stadium(stadium)
                .request(addMatch(1, "all", "조심", LocalDateTime.now().plusHours(4)))
                .build();
        matchRepository.save(match);


        //when
        applicationService.applyMatch(match.getId(), member1); //경기 신청
        MatchResponse findMatch = matchService.get(match.getId()); //경기 상세 조회

        //then
        assertEquals("CLOSE", findMatch.getStatus()); //member1이 신청하여 경기 상태가 마감으로 변경
        assertThrows(IllegalArgumentException.class, () -> {
            applicationService.applyMatch(findMatch.getId(), member2); //member2는 경기 상태가 마감이라서 신청 불가 "마감 된 경기입니다."
        });
    }

    @Test
    @DisplayName("경기 신청 취소")
    void cancelMatch() {
        //given
        Stadium stadium = addStadium("couch", "깔끔", "서울특별시 1234");
        Member member = addMember("minyuk1234", "minyuk", "male", "minyuk@gmail.com");

        Match match = Match.builder()
                .stadium(stadium)
                .request(addMatch(1, "male", "조심", LocalDateTime.now().plusHours(2)))
                .build();
        matchRepository.save(match);

        ApplicationResponse applyResponse = applicationService.applyMatch(match.getId(), member); //신청

        //when
        ApplicationResponse cancelResponse = applicationService.cancelMatch(match.getId(), member);// 신청 취소
        MatchResponse findMatch = matchService.get(match.getId()); //경기 상세 조회

        //then
        assertEquals(0, applyResponse.getRest()); //신청 시 남은 자리수
        assertEquals("CLOSE", applyResponse.getStatus()); //신청 시 경기 상태

        assertEquals(1, cancelResponse.getRest()); //신청 취소 후 남은 자리수
        assertEquals("OPEN", cancelResponse.getStatus()); //신청 취소 후 경기 상태
        assertEquals("OPEN", findMatch.getStatus()); //신청 취소후 경기 상태
        assertEquals(0, findMatch.getApplicantNum()); //신청 취소후 경기 신청자수
    }

    @Test
    @DisplayName("경기 신청자 리스트")
    void getList() {
        //given
        Stadium stadium = addStadium("couch", "깔끔", "서울특별시 1234");
        Member member1 = addMember("aaa111", "a", "male", "aaa@gmail.com");
        Member member2 = addMember("bbb222", "b", "female", "bbb@gmail.com");
        Member member3 = addMember("ccc333", "c", "female", "ccc@gmail.com");
        Member member4 = addMember("ddd444", "d", "male", "ddd@gmail.com");

        Match match = Match.builder()
                .stadium(stadium)
                .request(addMatch(10, "all", "조심", LocalDateTime.now().plusHours(3)))
                .build();
        matchRepository.save(match);

        applicationService.applyMatch(match.getId(), member1); //신청
        applicationService.applyMatch(match.getId(), member2); //신청
        applicationService.applyMatch(match.getId(), member3); //신청
        applicationService.applyMatch(match.getId(), member4); //신청
        applicationService.cancelMatch(match.getId(), member1); //신청취소

        //when
        List<Application> memberList = applicationService.getList(match.getId());

        //then
        assertEquals(3, memberList.size());
        assertEquals("bbb222", memberList.get(0).getMember().getUid());
        assertEquals("ccc333", memberList.get(1).getMember().getUid());
        assertEquals("ddd444", memberList.get(2).getMember().getUid());
    }

    private Stadium addStadium(String name, String content, String address) {
        Stadium stadium = Stadium.builder()
                .files(null)
                .name(name)
                .content(content)
                .parking(true)
                .rental(false)
                .address(address)
                .likeCount(0L)
                .build();

        stadiumRepository.save(stadium);

        return stadium;
    }

    private Member addMember(String uid, String username, String gender, String email) {
        Member member = Member.builder()
                .uid(uid)
                .username(username)
                .gender(gender)
                .email(email)
                .role("ROLE_USER")
                .build();

        memberRepository.save(member);

        return member;
    }

    private MatchCreateRequest addMatch(int matchNum, String gender, String content, LocalDateTime startAt) {
        return MatchCreateRequest.builder()
                .matchNum(matchNum)
                .matchGender(gender)
                .content(content)
                .startAt(startAt)
                .build();
    }
}