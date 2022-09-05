package couch.football.service.stadium;

import couch.football.repository.stadium.StadiumRepository;
import couch.football.response.stadium.StadiumSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StadiumService {

    private final StadiumRepository stadiumRepository;

    public List<StadiumSearchResponse> searchAddress(String address) {
        return stadiumRepository.findAllBySearchOption(address).stream()
                .map(StadiumSearchResponse::new)
                .collect(Collectors.toList());
    }

}
