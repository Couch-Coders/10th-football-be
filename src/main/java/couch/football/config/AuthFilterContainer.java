package couch.football.config;

import org.springframework.web.filter.OncePerRequestFilter;

public class AuthFilterContainer {

    private OncePerRequestFilter authFilter;

    public OncePerRequestFilter getAuthFilter() {
        return authFilter;
    }

    public void setAuthFilter(OncePerRequestFilter authFilter) {
        this.authFilter = authFilter;
    }
}
