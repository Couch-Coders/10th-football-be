package couch.football.repository.match;

import couch.football.domain.match.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long>, MatchRepositoryCustom {
}
