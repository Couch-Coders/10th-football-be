package couch.football.request.stadium;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StadiumUpdateRequest {

    private String name;
    private String content;
    private Boolean parking;
    private Boolean rental;
    private String address;
    private List<String> files;

    @Builder
    public StadiumUpdateRequest(String name, String content, Boolean parking, Boolean rental, String address, List<String> files) {
        this.name = name;
        this.content = content;
        this.parking = parking;
        this.rental = rental;
        this.address = address;
        this.files = files;
    }
}
