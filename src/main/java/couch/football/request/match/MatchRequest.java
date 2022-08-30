package couch.football.request.match;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class MatchRequest {

    @NotBlank(message = "경기장을 선택해주세요.")
    private Long stadiumId;

    @NotBlank(message = "경기 인원을 입력해주세요.")
    private Integer matchNum;

    @NotBlank(message = "성별을 입력해주세요")
    private String matchGender;

    @NotBlank(message = "안내사항을 입력해주세요.")
    private String content;

    @NotBlank(message = "경기 일정을 입력해주세요")
    private LocalDateTime startAt;

    @Builder
    public MatchRequest(Long stadiumId, Integer matchNum, String matchGender, String content, LocalDateTime startAt) {
        this.stadiumId = stadiumId;
        this.matchNum = matchNum;
        this.matchGender = matchGender;
        this.content = content;
        this.startAt = startAt;
    }
}
