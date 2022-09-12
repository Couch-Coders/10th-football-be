package couch.football.response.match;

import couch.football.domain.match.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {

    private Long id;
    private String uid;
    private String username;
    private String content;
    private LocalDateTime createdDate;

    @Builder
    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.uid = review.getMember().getUid();
        this.username = review.getMember().getUsername();
        this.content = review.getContent();
        this.createdDate = review.getCreatedDate();
    }

}
