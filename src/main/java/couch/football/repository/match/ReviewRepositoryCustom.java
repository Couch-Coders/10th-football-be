package couch.football.repository.match;

import couch.football.domain.match.Review;

public interface ReviewRepositoryCustom {

    Review findByUidAndMatchId(String uid, Long matchId);

}
