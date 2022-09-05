package couch.football.request.stadium;

import couch.football.domain.stadium.File;
import couch.football.domain.stadium.Stadium;
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
    private List<File> files;

    @Builder
    public StadiumUpdateRequest(String name, String content, Boolean parking, Boolean rental, String address, List<File> files) {
        this.name = name;
        this.content = content;
        this.parking = parking;
        this.rental = rental;
        this.address = address;
        this.files = files;
    }

    static public Stadium mapToEntity (StadiumUpdateRequest stadiumUpdateRequest) {

        return Stadium.builder()
                .name(stadiumUpdateRequest.getName())
                .content(stadiumUpdateRequest.getContent())
                .parking(stadiumUpdateRequest.getParking())
                .rental(stadiumUpdateRequest.getRental())
                .address(stadiumUpdateRequest.getAddress())
                .files(stadiumUpdateRequest.getFiles())
                .build();
    }
}
