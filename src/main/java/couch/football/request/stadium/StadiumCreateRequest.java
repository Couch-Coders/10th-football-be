package couch.football.request.stadium;

import couch.football.domain.stadium.File;
import couch.football.domain.stadium.Stadium;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StadiumCreateRequest {

    @NotBlank(message = "경기장 이름을 입력해야합니다")
    private String name;

    @NotBlank(message = "경기장 특이사항을 입력해야합니다")
    private String content;

    private Boolean parking;

    private Boolean rental;

    @NotBlank(message = "경기장 주소를 입력해야합니다")
    private String address;

    private List<String> files;

    @Builder
    public StadiumCreateRequest(String name, String content, Boolean parking, Boolean rental, String address, List<String> files) {
        this.name = name;
        this.content = content;
        this.parking = parking;
        this.rental = rental;
        this.address = address;
        this.files = files;
    }

    static public Stadium mapToEntity(StadiumCreateRequest staidumCreateReqeust) {

        return Stadium.builder()
                .name(staidumCreateReqeust.getName())
                .content(staidumCreateReqeust.getContent())
                .parking(staidumCreateReqeust.getParking())
                .rental(staidumCreateReqeust.getRental())
                .address(staidumCreateReqeust.getAddress())
                .build();
    }
}
