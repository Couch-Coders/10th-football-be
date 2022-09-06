package couch.football.response.match;

import couch.football.domain.match.Application;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ApplicationListResponse {

    private Long applicationId;
    private MatchesResponse match;

    @Builder
    public ApplicationListResponse(Application application) {
        this.applicationId = application.getId();
        this.match = new MatchesResponse(application.getMatch());
    }
}
