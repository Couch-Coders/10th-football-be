package couch.football.config;

import com.google.firebase.auth.FirebaseAuth;
import couch.football.filter.JwtFilter;
import couch.football.filter.MockJwtFilter;
import couch.football.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
public class AuthConfig {

    private final MemberService memberService;
    private final FirebaseAuth firebaseAuth;

    @Bean
    @Profile("prod")
//    @Profile({"local", "default"})
    public AuthFilterContainer filterContainer() {
        AuthFilterContainer authFilterContainer = new AuthFilterContainer();
        authFilterContainer.setAuthFilter(new JwtFilter(memberService, firebaseAuth));
        return authFilterContainer;
    }

    @Bean
    @Profile({"local", "default"})
//    @Profile("prod")
    public AuthFilterContainer mockAuthFilter() {
        AuthFilterContainer authFilterContainer = new AuthFilterContainer();
        authFilterContainer.setAuthFilter(new MockJwtFilter(memberService));
        return authFilterContainer;
    }
}
