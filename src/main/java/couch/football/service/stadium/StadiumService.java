package couch.football.service.stadium;

import couch.football.domain.match.Match;
import couch.football.domain.match.MatchStatus;
import couch.football.domain.stadium.File;
import couch.football.domain.stadium.Stadium;
import couch.football.exception.CustomException;
import couch.football.exception.ErrorCode;
import couch.football.repository.match.MatchRepository;
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

import static javax.persistence.CascadeType.ALL;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class StadiumService {

    private final StadiumRepository stadiumRepository;
    private final MatchRepository matchRepository;
    private final FileRepository fileRepository;

    public List<StadiumSearchResponse> searchAddress(String address) {
        return stadiumRepository.findAllBySearchOption(address).stream()
                .map(StadiumSearchResponse::new)
                .collect(Collectors.toList());
    }

    // 경기장 등록
    @Transactional
    public StadiumResponse createStadium(StadiumCreateRequest stadiumCreateRequest) {

        Stadium stadium = Stadium.builder()
                .name(stadiumCreateRequest.getName())
                .content(stadiumCreateRequest.getContent())
                .parking(stadiumCreateRequest.getParking())
                .rental(stadiumCreateRequest.getRental())
                .address(stadiumCreateRequest.getAddress())
                .build();

        stadium = stadiumRepository.save(stadium);

        List<File> files = new ArrayList<>();

        for(String imageUrl : stadiumCreateRequest.getFiles()) {
            File file = File.builder()
                    .stadium(stadium)
                    .imageUrl(imageUrl)
                    .build();
            file = fileRepository.save(file);
            files.add(file);
        }

        StadiumResponse stadiumResponse = StadiumResponse.mapToDto(stadium);

        stadiumResponse.setFiles(files);

        return stadiumResponse;
    }

    // 경기장 전체조회
    public List<StadiumResponse> getAllStadium() {

        List<Stadium> stadiumList = stadiumRepository.findAll();

        return stadiumList.stream()
                .map(StadiumResponse::mapToDto)
                .collect(Collectors.toList());
    }

    // 경기장 한건조회
    public StadiumResponse getByIdStadium(Long stadiumId) {

        Stadium findStadium = findStadium(stadiumId);

        return StadiumResponse.mapToDto(findStadium);
    }

    // 경기장 업데이트
    @Transactional
    public StadiumResponse updateStadium(Long stadiumId, StadiumUpdateRequest stadiumUpdateRequest) {

        Stadium findStadium = findStadium(stadiumId);

        List<File> files = new ArrayList<>();

        for(String imageUrl : stadiumUpdateRequest.getFiles()) {
            File file = File.builder()
                    .stadium(findStadium)
                    .imageUrl(imageUrl)
                    .build();
                file = fileRepository.save(file);
                files.add(file);
        }

        findStadium.updateStadium(stadiumUpdateRequest);

        StadiumResponse updateStadium = StadiumResponse.mapToDto(findStadium);

        updateStadium.setFiles(files);

        return updateStadium;
    }

    // 경기장 삭제
    @Transactional
    public void deleteStadium(Long stadiumId) {

        Stadium findStadium = findStadium(stadiumId);

        List<Match> matches = matchRepository.findByStadiumId(stadiumId);
        boolean flag = false;
        for (Match match : matches) {
            if (match.getStatus().equals(MatchStatus.OPEN)) {
                flag = true;
            }
        }

        if (flag) {
            throw new CustomException(ErrorCode.EXIST_MATCH);
        } else {
            stadiumRepository.delete(findStadium);
        }

    }

    private Stadium findStadium(Long stadiumId) {

        return stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "경기장이 존재하지 않습니다"));

    }

}
