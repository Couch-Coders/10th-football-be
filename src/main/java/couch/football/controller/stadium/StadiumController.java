package couch.football.controller.stadium;

import couch.football.response.stadium.StadiumSearchResponse;
import couch.football.service.stadium.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("stadiums")
public class StadiumController {

    private final StadiumService stadiumService;

    @GetMapping("")
    public List<StadiumSearchResponse> getList(@RequestParam(value = "address", required = false) String address) {
        return stadiumService.searchAddress(address);
    }
}
