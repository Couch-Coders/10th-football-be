package couch.football.controller;

import couch.football.domain.match.Match;
import couch.football.repository.match.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Test;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class TestController {

    private final MatchRepository matchRepository;

    @PostMapping("/matches")
    public Match add(@RequestBody TestDTO request) {
        Match match = Match.builder()
                .gender(null)
                .stadium(null)
                .startAt(null)
                .status(null)
                .matchNum(request.getMatchNum())
                .applicantNum(request.getApplicantNum())
                .content(request.getContent())
                .build();

        return matchRepository.save(match);
    }

    @GetMapping("/matches/{matchId}")
    public TestDTO get(@PathVariable Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(null);

        return TestDTO.builder()
                .id(match.getId())
                .matchNum(match.getMatchNum())
                .applicantNum(match.getApplicantNum())
                .content(match.getContent())
                .build();
    }

    @PostMapping("/test")
    public TestDTO test(@RequestBody TestDTO testDTO) {
        return testDTO;
    }
}
