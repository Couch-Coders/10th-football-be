package couch.football.repository.match;

import couch.football.domain.match.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long>, MatchRepositoryCustom {

    List<Match> findByStadiumId(Long stadiumId);

}
