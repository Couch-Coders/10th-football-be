package couch.football.repository.match;

import couch.football.domain.match.Application;

import java.util.List;

public interface ApplicationRepositoryCustom {

    List<Application> findAllByUidAndMatchId(String uid, Long matchId);

}
