package couch.football.response.match;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MatchStadiumResponse {
    private Long id;
    private String name;
    private String content;
    private Boolean parking;
    private Boolean rental;
    private String address;
    private Long likeCount;
    // TODO: 경기장 사진 url 추가하기


    @Builder
    public MatchStadiumResponse(Long id, String name, String content, Boolean parking, Boolean rental, String address, Long likeCount) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.parking = parking;
        this.rental = rental;
        this.address = address;
        this.likeCount = likeCount;
    }
}
