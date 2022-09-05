package couch.football.repository.match;

import couch.football.domain.match.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface MatchRepositoryCustom {

    Page<Match> findAllBySearchOption(Pageable pageable, LocalDate matchDay, String gender, String deadline, Integer personnel, String stadiumName);

    Optional<Match> findByIdWithFetchJoinStadium(Long matchId);
}
