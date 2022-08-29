package couch.football.service.match;

import couch.football.domain.match.Match;
import couch.football.domain.stadium.Stadium;
import couch.football.repository.match.MatchRepository;
import couch.football.repository.stadium.StadiumRepository;
import couch.football.request.match.MatchCreate;
import org.junit.jupiter.api.Assertions;
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

    @Test
    @DisplayName("매치 생성")
    void createMatch() {
        //given
        addStadium();
        MatchCreate request = MatchCreate.builder()
                .stadiumId(stadiumRepository.findAll().get(0).getId())
                .matchNum(12)
                .matchGender("MALE")
                .content("조심")
                .startAt(null)
                .build();

        //when
        matchService.createMatch(request);

        //then
        assertEquals(1L, matchRepository.count());Match match = matchRepository.findAll().get(0);
        assertEquals(12, match.getMatchNum());
        assertEquals("MALE", match.getGender().toString());
        assertEquals("조심", match.getContent());
        assertNull(match.getStartAt());
    }

    void addStadium() {
        Stadium stadium = Stadium.builder()
                .files(null)
                .name("couch")
                .content("clean")
                .parking(true)
                .rental(false)
                .address("서울특별시 영등포구 양평동 1234")
                .likeCount(0L)
                .build();

        stadiumRepository.save(stadium);
    }
}