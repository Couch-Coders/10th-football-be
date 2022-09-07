package couch.football.config;

import com.google.cloud.storage.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthFilterContainer authFilterContainer;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
              .formLogin().disable()
              .httpBasic().disable()//rest api만 고려
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              //시큐리티가 세션을 생성하지 않음(JWT를 사용)
              .and()
              .csrf().disable()
              .addFilterBefore(authFilterContainer.getAuthFilter(),
                      UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/users") //로그인은 로그인이 필요 없다
                .antMatchers("/favicon.ico");
    }
}
