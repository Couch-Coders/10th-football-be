package couch.football.response.stadium;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeResponse {

    private Long stadiumId;
    private String uid;
    private Long likeCount;

    @Builder
    public LikeResponse(Long stadiumId, String uid, Long likeCount) {
        this.stadiumId = stadiumId;
        this.uid = uid;
        this.likeCount = likeCount;
    }
}
