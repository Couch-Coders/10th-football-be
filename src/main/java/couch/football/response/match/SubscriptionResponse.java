package couch.football.response.match;

import couch.football.domain.match.Subscription;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SubscriptionResponse {

    private final Long id;
    private final String status;
    private final int rest;
    private final Subscription subscription;

    @Builder
    public SubscriptionResponse(Long id, String status, int rest, Subscription subscription) {
        this.id = id;
        this.status = status;
        this.rest = rest;
        this.subscription = subscription;
    }
}
