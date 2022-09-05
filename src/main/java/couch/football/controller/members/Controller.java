package couch.football.controller.members;

import com.google.firebase.auth.FirebaseToken;
import couch.football.domain.member.Member;
import couch.football.domain.member.Role;
import couch.football.request.members.MemberInfoRequestDto;
import couch.football.response.members.MemberResponseDto;
import couch.football.service.member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class Controller {

    private final MemberService memberService;
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
                                                        @RequestBody MemberInfoRequestDto memberInfoRequestDto) {

        FirebaseToken decodedToken = memberService.decodeToken(header);

        Member memberRegister = Member.builder()
                .uid(decodedToken.getUid())
                .username(decodedToken.getName())
                .email(decodedToken.getEmail())
                .phone(memberInfoRequestDto.getPhone())
                .gender(memberInfoRequestDto.getGender())
                .role(Role.USER)
                .build();

        MemberResponseDto responseDto = memberService.saveMember(memberRegister);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);

    }



    //local 가입
    @PostMapping("/local/users")
    public ResponseEntity<MemberResponseDto> localJoinMember(@RequestHeader("Authorization") String header,
                                                             @RequestBody MemberInfoRequestDto memberInfoRequestDto) {

        String decodedToken = memberService.testDecodeToken(header);

        Member memberRegister = Member.builder()
                .uid(decodedToken)
                .username("test")
                .email("test@email.com")
                .phone(memberInfoRequestDto.getPhone())
                .gender(memberInfoRequestDto.getGender())
                .role(Role.USER)
                .build();

        MemberResponseDto responseDto = memberService.saveMember(memberRegister);

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
