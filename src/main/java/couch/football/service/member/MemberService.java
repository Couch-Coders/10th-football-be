package couch.football.service.member;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import couch.football.domain.member.Member;
import couch.football.domain.member.Role;
import couch.football.exception.CustomException;
import couch.football.exception.ErrorCode;
import couch.football.repository.member.MemberRepository;
import couch.football.request.members.MemberInfoRequestDto;
import couch.football.response.members.MemberResponseDto;
import couch.football.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final FirebaseAuth firebaseAuth;

    @Transactional
    public MemberResponseDto saveMember(String header, MemberInfoRequestDto request) {
        FirebaseToken decodedToken = decodeToken(header);

        Member member = Member.builder()
                .uid(decodedToken.getUid())
                .username(decodedToken.getName())
                .email(decodedToken.getEmail())
                .phone(request.getPhone())
                .gender(request.getGender())
                .role(Role.ROLE_USER)
                .build();

        duplicateCheck(member);

        return new MemberResponseDto(memberRepository.save(member));
    }

    private void duplicateCheck(Member member) {
        boolean existsMember = memberRepository.existsByUid(member.getUid());
        if (existsMember) {
            throw new IllegalArgumentException("?????? ????????? ???????????????.");
        }
    }

    //?????? ?????? ?????????
    public FirebaseToken decodeToken(String header) {

        try {

            String token = TokenUtil.getAuthorizationToken(header);
            return firebaseAuth.verifyIdToken(token);

        } catch (IllegalArgumentException | FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "message: "+e.getMessage());
        }
    }




    //Spring Security?????? ????????? ????????? ???????????? ???????????????
    //??? ?????????????????? ?????? ????????? security??? ????????? ??????. -> filter
    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        return (UserDetails) memberRepository.findByUid(uid).orElseThrow(() -> {
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
        });
    }

    //post test
    public String testDecodeToken(String header) {

        try {

            String token = TokenUtil.getAuthorizationToken(header);

            return token;

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "message: "+e.getMessage());
        }
    }

    @Transactional
    public MemberResponseDto testSaveMember(String header, MemberInfoRequestDto request) {

        String decodedToken = testDecodeToken(header);

        Member member = Member.builder()
                .uid(decodedToken)
                .username("test")
                .email("test@email.com")
                .phone(request.getPhone())
                .gender(request.getGender())
                .role(Role.ROLE_USER)
                .build();

        return new MemberResponseDto(memberRepository.save(member));
    }

}
