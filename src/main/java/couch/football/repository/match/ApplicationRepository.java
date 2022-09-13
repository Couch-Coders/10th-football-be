package couch.football.repository.match;

import couch.football.domain.match.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long>, ApplicationRepositoryCustom {

    List<Application> findByMatchId(Long matchId);

}
