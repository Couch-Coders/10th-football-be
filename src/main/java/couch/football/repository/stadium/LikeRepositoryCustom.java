package couch.football.repository.stadium;

import couch.football.domain.stadium.Like;

import java.util.List;

public interface LikeRepositoryCustom {

    List<Like> findAllByUidAndStadiumId(String uid, Long stadiumId);

}
