package couch.football.repository.stadium;

import couch.football.domain.stadium.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StadiumRepository extends JpaRepository<Stadium, Long>, StadiumRepositoryCustom{
}
