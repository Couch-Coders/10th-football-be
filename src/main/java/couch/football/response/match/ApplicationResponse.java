package couch.football.response.match;

import couch.football.domain.match.Application;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ApplicationResponse {

    private final Long id;
    private final String status;
    private final int rest;
    private final Application application;

    @Builder
    public ApplicationResponse(Long id, String status, int rest, Application application) {
        this.id = id;
        this.status = status;
        this.rest = rest;
        this.application = application;
    }
}
