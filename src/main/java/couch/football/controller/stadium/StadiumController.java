package couch.football.controller.stadium;

import couch.football.request.stadium.StadiumCreateRequest;
import couch.football.request.stadium.StadiumUpdateRequest;
import couch.football.response.stadium.StadiumResponse;
import couch.football.response.stadium.StadiumSearchResponse;
import couch.football.service.stadium.StadiumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("stadiums")
public class StadiumController {

    private final StadiumService stadiumService;

    @GetMapping("")
    public List<StadiumSearchResponse> getList(@RequestParam(value = "address", required = false) String address) {
        return stadiumService.searchAddress(address);
    }

    // 경기장 생성
    @PostMapping("")
    public ResponseEntity<StadiumResponse> createStadium(@Valid @RequestBody StadiumCreateRequest stadiumCreateRequest) {

        StadiumResponse stadium = stadiumService.createStadium(stadiumCreateRequest);

        return new ResponseEntity<>(stadium, HttpStatus.CREATED);
    }

    // 경기장 전체 조회
    @GetMapping("/All")
    public ResponseEntity<List<StadiumResponse>> getStadium() {

        return ResponseEntity.ok(stadiumService.getAllStadium());
    }

    // 경기장 한건 조회
    @GetMapping("/{stadiumId}")
    public ResponseEntity<StadiumResponse> getByIdStadium(@PathVariable("stadiumId") Long stadiumId) {

        return ResponseEntity.ok(stadiumService.getByIdStadium(stadiumId));
    }

    // 경기장 수정
    @PatchMapping("/{stadiumId}")
    public ResponseEntity<?> updateStadium(@PathVariable("stadiumId") Long stadiumId,
                                           @Valid @RequestBody StadiumUpdateRequest stadiumUpdateRequest) {

        return ResponseEntity.ok(stadiumService.updateStadium(stadiumId, stadiumUpdateRequest));
    }

    // 경기장 삭제
    @DeleteMapping("/{stadiumId}")
    public ResponseEntity<?> deleteStadium(@PathVariable("stadiumId") Long stadiumId) {

        stadiumService.deleteStadium(stadiumId);

        return ResponseEntity.ok("경기장 삭제가 완료되었습니다.");
    }
}
