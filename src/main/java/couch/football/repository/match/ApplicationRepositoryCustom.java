package couch.football.repository.match;

import couch.football.domain.match.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApplicationRepositoryCustom {

    List<Application> findAllByUidAndMatchId(String uid, Long matchId);

    Page<Application> findAllByUid(Pageable pageable, String uid);

}
