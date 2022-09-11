package couch.football.repository.stadium;

import couch.football.domain.stadium.Stadium;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
//@DataJpaTest
@Transactional
@Slf4j
class StadiumRepositoryTest {

    @Autowired
    private StadiumRepository stadiumRepository;

    private Stadium stadium1, stadium2;

    @BeforeEach
    void setup() {

        stadium1 = Stadium.builder()
                .name("더 플러스 풋살장")
                .content("흡연은 불가능 합니다.")
                .parking(false)
                .rental(true)
                .likeCount(20L)
                .address("서울시 구로구 구로동 1234")
                .files(null)
                .build();

        stadium2 = Stadium.builder()
                .name("더 에프 풋살장")
                .content("흡연 가능 합니다.")
                .parking(false)
                .rental(true)
                .likeCount(20L)
                .address("서울시 영등포구 양평동 1234")
                .files(null)
                .build();
    }

    @Test
    @DisplayName("경기장 생성")
    public void create_Stadium() {
        // given
        Stadium savedStadium1 = stadiumRepository.save(stadium1);
        Stadium savedStadium2 = stadiumRepository.save(stadium2);
        log.info("saveStadium1: {}", savedStadium1);
        log.info("saveStadium2: {}", savedStadium2);

        // when

        // then
        assertThat(savedStadium1.getName()).isEqualTo("더 플러스 풋살장");
        assertThat(savedStadium2.getName()).isEqualTo("더 에프 풋살장");
    }

    @Test
    @DisplayName("경기장 단건 조회")
    public void findOneStadium() {
        // given
        Stadium savedStadium = stadiumRepository.save(stadium1);
        Stadium stadium = stadiumRepository.findById(savedStadium.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "경기장이 존재하지 않습니다"));

        // when

        // then
        assertThat(stadium).isEqualTo(savedStadium);
    }

    @Test
    @DisplayName("경기장 리스트 조회")
    public void findStadiumList() {
        // given
        stadiumRepository.save(stadium1);
        stadiumRepository.save(stadium2);

        List<Stadium> result = stadiumRepository.findAll();
        log.info("result: {}", result);

        // when
        long count = stadiumRepository.count();

        // then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("경기장 삭제")
    public void deleteStadium() {
        // given
        stadiumRepository.save(stadium1);
        stadiumRepository.save(stadium2);

        stadiumRepository.delete(stadium1);

        // when
        long deletedCount = stadiumRepository.count();

        // then
        assertThat(deletedCount).isEqualTo(1);
    }
}