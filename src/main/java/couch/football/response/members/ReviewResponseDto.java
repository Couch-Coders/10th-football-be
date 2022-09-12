package couch.football.response.members;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {

    private Long id;
    private Long matchId;
    private String uId;
    private String content;
    private LocalDateTime createdDate;

    @Builder
    public ReviewResponseDto(Long id, Long matchId, String uId, String content, LocalDateTime createdDate) {
        this.id = id;
        this.matchId = matchId;
        this.uId = uId;
        this.content = content;
        this.createdDate = createdDate;
    }
}
