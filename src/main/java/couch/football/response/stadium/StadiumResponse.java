package couch.football.response.stadium;

import couch.football.domain.stadium.File;
import couch.football.domain.stadium.Stadium;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StadiumResponse {

    private Long id;
    private String name;
    private String content;
    private Boolean parking;
    private Boolean rental;
    private String address;
    private List<FileResponse> files;

    @Builder
    public StadiumResponse(Long id, String name, String content, Boolean parking, Boolean rental, String address, List<File> files) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.parking = parking;
        this.rental = rental;
        this.address = address;
        setFiles(files);
    }

    public void setFiles(List<File> files){
        if(files != null) {
            this.files = files.stream().map(FileResponse::new).collect(Collectors.toList());
        }
    }

    static public StadiumResponse mapToDto(Stadium stadium) {

        return StadiumResponse.builder()
                .id(stadium.getId())
                .name(stadium.getName())
                .content(stadium.getContent())
                .parking(stadium.getParking())
                .rental(stadium.getRental())
                .address(stadium.getAddress())
                .files(stadium.getFiles())
                .build();
    }
}
