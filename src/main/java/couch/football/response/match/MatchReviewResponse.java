package couch.football.response.match;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MatchReviewResponse {
    private Long id;
    private String content;
    // TODO: 멤버 정보 추가 예정
}
