package couch.football.service.match;

import couch.football.domain.match.Match;
import couch.football.domain.match.MatchGender;
import couch.football.domain.match.MatchStatus;
import couch.football.domain.stadium.Stadium;
import couch.football.repository.match.MatchRepository;
import couch.football.repository.stadium.StadiumRepository;
import couch.football.request.match.MatchRequest;
import couch.football.response.match.MatchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MatchServiceTest {

    @Autowired
    private MatchService matchService;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

    @BeforeEach
    void clean() {
        matchRepository.deleteAll();
    }

    //Stadium, Match 생성 -> (add+Entity) 메서드 사용하기
    @Test
    @DisplayName("경기 생성")
    void create() {
        //given
        Stadium stadium = addStadium("하늘풋살장", "흡연 금지", "서울특별시 영등포구 1234");

        MatchRequest request = MatchRequest.builder()
                .stadiumId(stadium.getId())
                .matchNum(12)
                .matchGender("all")
                .startAt(null)
                .content("조심조심")
                .build();

        //when
        matchService.create(request);

        //then
        assertEquals(1L, matchRepository.count());
        Match match = matchRepository.findAll().get(0);
        assertEquals(12, match.getMatchNum());
        assertEquals("ALL", match.getGender().toString());
        assertEquals("조심조심", match.getContent());
        assertNull(match.getStartAt());
    }

    @Test
    @DisplayName("경기 수정")
    void update() {
        //given
        Stadium stadium1 = addStadium("하늘풋살장", "흡연 금지", "서울특별시 영등포구 1234");
        Stadium stadium2 = addStadium("서울풋살장", "신축", "서울특별시 송파구 2222");
        Match match = addMatch(stadium1, 12, "all", "조심조심");

        MatchRequest matchUpdate = MatchRequest.builder()
                .stadiumId(stadium2.getId())
                .matchNum(10)
                .matchGender("male")
                .content("수정수정")
                .startAt(match.getStartAt())
                .build();

        //when
        matchService.update(match.getId(), matchUpdate);

        //then
        MatchResponse matchResponse = matchService.get(match.getId());
        assertEquals(stadium2.getId(), matchResponse.getStadium().getId());
        assertEquals(10, matchResponse.getMatchNum());
        assertEquals("수정수정", matchResponse.getContent());
        assertEquals("MALE", matchResponse.getGender());
    }

    @Test
    @DisplayName("경기 삭제")
    void delete() {
        //given
        Stadium stadium = addStadium("하늘풋살장", "흡연 금지", "서울특별시 영등포구 1234");
        Match match = addMatch(stadium, 12, "all", "조심조심");

        //when
        matchService.delete(match.getId());

        //then
        assertEquals(0, matchRepository.count());
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

    private Match addMatch(Stadium stadium, int matchNum, String gender, String content) {
        Match match = Match.builder()
                .stadium(stadium)
                .matchNum(matchNum)
                .gender(MatchGender.valueOf(gender.toUpperCase()))
                .content(content)
                .startAt(null)
                .build();

        matchRepository.save(match);

        return match;
    }


}