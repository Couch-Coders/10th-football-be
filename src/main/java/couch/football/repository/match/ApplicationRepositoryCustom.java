package couch.football.repository.match;

import couch.football.domain.match.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationRepositoryCustom {

    Application findByUidAndMatchId(String uid, Long matchId);

    Page<Application> findAllByUid(Pageable pageable, String uid);

}
