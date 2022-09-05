package couch.football;

import couch.football.domain.match.Application;
import couch.football.domain.match.Match;
import couch.football.domain.member.Member;
import couch.football.domain.stadium.Stadium;
import couch.football.request.match.MatchCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit() {
            Member member1 = addMember("aaa111", "a", "male", "aaa@gmail.com", "010-1234-5678");
            em.persist(member1);

            Member member2 = addMember("bbb222", "b", "female", "bbb@gmail.com", "010-2222-3333");
            em.persist(member2);

            Stadium stadium1 = addStadium("하늘풋살장", "깔끔", "서울특별시 영등포구 1234");
            em.persist(stadium1);

            Stadium stadium2 = addStadium("옥상풋살장", "더러움", "서울특별시 구로구 3333");
            em.persist(stadium2);

            Stadium stadium3 = addStadium("실내풋살장", "더움", "부산광역시 동래구 0000");
            em.persist(stadium3);

            Stadium stadium4 = addStadium("에이풋살장", "넓음", "광주광역시 북구 5678");
            em.persist(stadium4);

            Match match1 = Match.builder()
                    .stadium(stadium1)
                    .request(addMatch(LocalDateTime.now().plusHours(1), "테스트1", 10, "MALE"))
                    .build();
            em.persist(match1);

            Match match2 = Match.builder()
                    .stadium(stadium1)
                    .request(addMatch(LocalDateTime.now().plusHours(2), "테스트2", 12, "FEMALE"))
                    .build();
            em.persist(match2);

            Match match3 = Match.builder()
                    .stadium(stadium2)
                    .request(addMatch(LocalDateTime.now().plusDays(2), "테스트3", 16, "ALL"))
                    .build();
            em.persist(match3);

            Match match4 = Match.builder()
                    .stadium(stadium2)
                    .request(addMatch(LocalDateTime.now().plusDays(3).plusHours(4), "테스트4", 12, "MALE"))
                    .build();
            em.persist(match4);

            Match match5 = Match.builder()
                    .stadium(stadium2)
                    .request(addMatch(LocalDateTime.now().plusDays(3).plusHours(6), "테스트5", 10, "ALL"))
                    .build();
            em.persist(match5);

            Match match6 = Match.builder()
                    .stadium(stadium2)
                    .request(addMatch(LocalDateTime.now().plusDays(3).plusHours(1), "테스트6", 12, "MALE"))
                    .build();
            em.persist(match6);

            Application applicant1 = Application.builder()
                    .match(match1)
                    .member(member1)
                    .build();
            em.persist(applicant1);

            Application applicant2 = Application.builder()
                    .match(match1)
                    .member(member2)
                    .build();
            em.persist(applicant2);
        }

        private Stadium addStadium(String name, String content, String address) {
            return Stadium.builder()
                    .files(null)
                    .name(name)
                    .content(content)
                    .parking(true)
                    .rental(false)
                    .address(address)
                    .likeCount(0L)
                    .build();
        }

        private Member addMember(String uid, String username, String gender, String email, String phone) {
            return Member.builder()
                    .uid(uid)
                    .username(username)
                    .gender(gender)
                    .email(email)
                    .phone(phone)
                    .role("ROLE_USER")
                    .build();
        }

        private MatchCreateRequest addMatch(LocalDateTime startAt, String content, Integer matchNum, String gender) {
            return MatchCreateRequest.builder()
                    .startAt(startAt)
                    .content(content)
                    .matchNum(matchNum)
                    .matchGender(gender)
                    .build();
        }
    }

}
