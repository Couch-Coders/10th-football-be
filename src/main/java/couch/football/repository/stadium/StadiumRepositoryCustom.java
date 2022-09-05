package couch.football.repository.stadium;

import couch.football.domain.stadium.Stadium;

import java.util.List;

public interface StadiumRepositoryCustom {

    List<Stadium> findAllBySearchOption(String address);

}
