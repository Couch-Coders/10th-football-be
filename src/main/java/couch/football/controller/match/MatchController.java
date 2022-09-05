package couch.football.controller.match;

import couch.football.domain.member.Member;
import couch.football.request.match.MatchCreateRequest;
import couch.football.request.match.MatchUpdateRequest;
import couch.football.response.match.ApplicationResponse;
import couch.football.response.match.MatchResponse;
import couch.football.response.match.MatchesResponse;
import couch.football.service.match.ApplicationService;
import couch.football.service.match.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("matches")
public class MatchController {

    private final MatchService matchService;
    private final ApplicationService applicationService;

    @PostMapping("")
    public void create(@RequestBody @Valid MatchCreateRequest request) {
        matchService.create(request);
    }

    @GetMapping("")
    public Page<MatchesResponse> getList(Pageable pageable,
                                         @RequestParam(value = "matchDay", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate matchDay,
                                         @RequestParam(value = "gender", required = false) String gender,
                                         @RequestParam(value = "status", required = false) String status,
                                         @RequestParam(value = "personnel", required = false) Integer personnel,
                                         @RequestParam(value = "stadiumName", required = false) String stadiumName) {

        return matchService.getList(pageable, matchDay, gender, status, personnel, stadiumName);
    }

    @GetMapping("/{matchId}")
    public MatchResponse get(@PathVariable Long matchId) {
        return matchService.get(matchId);
    }

    @PatchMapping("/{matchId}")
    public void update(@PathVariable Long matchId, @RequestBody @Valid MatchUpdateRequest request) {
        matchService.update(matchId, request);
    }

    @DeleteMapping("/{matchId}")
    public void delete(@PathVariable Long matchId) {
        matchService.delete(matchId);
    }

    @PostMapping("/applications/{matchId}")
    public ApplicationResponse apply(@PathVariable Long matchId) {
        // TODO: 로그인 구현 완료 시 수정 할 예정, service 구현 완료
        Member member = null;
        return applicationService.applyMatch(matchId, member);
    }

    @DeleteMapping("/applications/{matchId}")
    public ApplicationResponse applyCancel(@PathVariable Long matchId) {
        // TODO: 로그인 구현 완료 시 수정 할 예정, service 구현 완료
        Member member = null;
        return applicationService.applyMatch(matchId, member);
    }

    @GetMapping("/test")
    public void test(Authentication authentication){
        Member member = (Member) authentication.getPrincipal();
        System.out.println(member.getUid());
        System.out.println(member.getUsername());
    }
}
