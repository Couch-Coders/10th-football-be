package couch.football.controller.member;

import couch.football.domain.member.Member;
import couch.football.request.members.MemberInfoRequestDto;
import couch.football.response.match.ApplicationListResponse;
import couch.football.response.match.ReviewResponseDto;
import couch.football.response.members.MemberResponseDto;
import couch.football.service.match.ApplicationService;
import couch.football.service.match.ReviewService;
import couch.football.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
//@CrossOrigin("https://football-3b39f.web.app/")
public class MemberController {

    private final MemberService memberService;
    private final ApplicationService applicationService;
    private final ReviewService reviewService;

    @GetMapping("/users/me/applications")
    public Page<ApplicationListResponse> getApplicationList(Pageable pageable, Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();

        return applicationService.getApplications(pageable, member);
    }

    @GetMapping("/users/me/reviews/{matchId}")
    public ReviewResponseDto getReview(@PathVariable Long matchId, Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();

        return reviewService.get(member, matchId);
    }

    //로그인
    //로그인 버튼을 누른 후, 회원이면 정보 return 아니면 회원 가입 절차 (@PostMapping("/users"))
    @GetMapping("/users/me")
    public ResponseEntity<MemberResponseDto> findMember(Authentication authentication) {
        //filter에서 header를 decode해서 회원이 있는지 없는지 구분, 없으면 401 있으면 아래 로직을 실행한다.
        //회원이기 때문에 회원 정보를 이용해 return 해준다.
        Member member = (Member) authentication.getPrincipal();

        return ResponseEntity.ok(new MemberResponseDto(member));
    }

    //회원가입(없는 회원으로 @GetMapping("/users/me")에서 401이 return 됨)
    @PostMapping("/users")
    public ResponseEntity<MemberResponseDto> joinMember(@RequestHeader("Authorization") String header,
                                                        @RequestBody @Valid MemberInfoRequestDto memberInfoRequestDto) {

        MemberResponseDto responseDto = memberService.saveMember(header, memberInfoRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);

    }

    //local 가입
    @PostMapping("/local/users")
    public ResponseEntity<MemberResponseDto> localJoinMember(@RequestHeader("Authorization") String header,
                                                             @RequestBody MemberInfoRequestDto memberInfoRequestDto) {

        MemberResponseDto responseDto = memberService.saveMember(header, memberInfoRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);

    }

    //local 접속
    @GetMapping("/local/users/me")
    public ResponseEntity<MemberResponseDto> localFindMember(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();

        return ResponseEntity.ok(new MemberResponseDto(member));

    }

}
