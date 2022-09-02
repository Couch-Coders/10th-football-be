package couch.football.response.stadium;

import couch.football.domain.stadium.Stadium;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StadiumSearchResponse {
    private Long id;
    private String name;
    private String address;

    public StadiumSearchResponse(Stadium stadium) {
        this.id = stadium.getId();
        this.name = stadium.getName();
        this.address = stadium.getAddress();
    }
}
