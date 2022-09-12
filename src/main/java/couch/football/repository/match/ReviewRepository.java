package couch.football.repository.match;

import couch.football.domain.match.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    List<Review> findByMatchId(Long matchId);

}
