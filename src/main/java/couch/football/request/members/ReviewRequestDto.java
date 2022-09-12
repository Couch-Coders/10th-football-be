package couch.football.request.members;

import lombok.Getter;

@Getter
public class ReviewRequestDto {
    private Long matchId;
    private String content;
}
