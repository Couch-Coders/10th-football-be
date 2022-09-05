package couch.football.response.match;

import couch.football.domain.match.Application;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationResponse {

    private Long id;
    private String status;
    private Integer rest;
    private Application application;

    @Builder
    public ApplicationResponse(Long id, String status, Integer rest, Application application) {
        this.id = id;
        this.status = status;
        this.rest = rest;
        this.application = application;
    }
}
