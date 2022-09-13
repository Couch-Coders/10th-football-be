package couch.football.repository.stadium;

import couch.football.domain.stadium.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeRepositoryCustom {

    List<Like> findByStadiumId(Long stadiumId);

}
