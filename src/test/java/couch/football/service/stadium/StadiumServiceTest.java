package couch.football.service.stadium;

import couch.football.domain.stadium.Stadium;
import couch.football.repository.stadium.StadiumRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Slf4j
class StadiumServiceTest {

    @Mock
    private StadiumRepository stadiumRepository;

    @InjectMocks
    private StadiumService stadiumService;

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

//    @Test
//    void updateStadium() {
//        // given
//        String newName = "풋살장";
//
//        // when
//        Stadium savedStadium = stadiumRepository.save(stadium1);
//        log.info("saveStadium: {}", savedStadium);
//
//        stadiumService.updateStadium(savedStadium.getId(), StadiumSaveRequestDto.builder().name(newName).build());
//
//        Stadium findStadium = stadiumRepository.findById(savedStadium.getId())
//                .orElseThrow(null);
//
//        // then
//        assertThat(findStadium.getName()).isEqualTo("풋살장");
//    }
}