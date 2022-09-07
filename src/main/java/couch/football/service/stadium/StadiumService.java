package couch.football.service.stadium;

import couch.football.domain.stadium.File;
import couch.football.domain.stadium.Stadium;
import couch.football.repository.stadium.FileRepository;
import couch.football.repository.stadium.StadiumRepository;
import couch.football.request.stadium.StadiumCreateRequest;
import couch.football.request.stadium.StadiumUpdateRequest;
import couch.football.response.stadium.StadiumResponse;
import couch.football.response.stadium.StadiumSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StadiumService {

    private final StadiumRepository stadiumRepository;
    private final FileRepository fileRepository;

    @Transactional(readOnly = true)
    public List<StadiumSearchResponse> searchAddress(String address) {
        return stadiumRepository.findAllBySearchOption(address).stream()
                .map(StadiumSearchResponse::new)
                .collect(Collectors.toList());
    }

    // 경기장 등록
    @Transactional
    public StadiumResponse createStadium(StadiumCreateRequest stadiumCreateRequest) {

        List<File> files = new ArrayList<>();
        for(String imageUrl : stadiumCreateRequest.getFiles()) {
            File file = File.builder()
                    .imageUrl(imageUrl)
                    .build();
            file = fileRepository.save(file);
            files.add(file);
        }

        Stadium stadium = Stadium.builder()
                .name(stadiumCreateRequest.getName())
                .content(stadiumCreateRequest.getContent())
                .parking(stadiumCreateRequest.getParking())
                .rental(stadiumCreateRequest.getRental())
                .address(stadiumCreateRequest.getAddress())
                .files(files)
                .build();

        Stadium savedStadium = stadiumRepository.save(stadium);

        return StadiumResponse.mapToDto(savedStadium);
    }

    // 경기장 전체조회
    @Transactional(readOnly = true)
    public List<StadiumResponse> getAllStadium() {

        List<Stadium> stadiumList = stadiumRepository.findAll();

        for (Stadium stadium : stadiumList) {
            log.info("stadiumList :{}", stadium);
        }

        return stadiumList.stream()
                .map(StadiumResponse::mapToDto)
                .collect(Collectors.toList());
    }

    // 경기장 한건조회
    @Transactional(readOnly = true)
    public StadiumResponse getByIdStadium(Long stadiumId) {

        Stadium findStadium = stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "경기장이 존재하지 않습니다"));

        return StadiumResponse.mapToDto(findStadium);
    }

    // 경기장 업데이트
    @Transactional
    public StadiumResponse updateStadium(Long stadiumId, StadiumUpdateRequest stadiumUpdateRequest) {

        Stadium findStadium = stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "경기장이 존재하지 않습니다"));

        findStadium.updateStadium(stadiumUpdateRequest);

        return StadiumResponse.mapToDto(findStadium);
    }

    // 경기장 삭제
    @Transactional
    public void deleteStadium(Long stadiumId) {

        Stadium findStadium = stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "경기장이 존재하지 않습니다"));

        stadiumRepository.delete(findStadium);
    }

}
