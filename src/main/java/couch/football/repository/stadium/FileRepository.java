package couch.football.repository.stadium;

import couch.football.domain.stadium.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
