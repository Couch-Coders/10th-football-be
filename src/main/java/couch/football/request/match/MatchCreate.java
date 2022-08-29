package couch.football.request.match;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class MatchCreate {

    @NotBlank(message = "경기장을 선택해주세요.")
    private Long stadiumId;

    @NotBlank(message = "경기 인원을 입력해주세요.")
    private int matchNum;

    @NotBlank(message = "성별을 입력해주세요")
    private String matchGender;

    @NotBlank(message = "안내사항을 입력해주세요.")
    private String content;

    @NotBlank(message = "경기 일정을 입력해주세요")
    private LocalDateTime startAt;

    @Builder
    public MatchCreate(Long stadiumId, int matchNum, String matchGender, String content, LocalDateTime startAt) {
        this.stadiumId = stadiumId;
        this.matchNum = matchNum;
        this.matchGender = matchGender;
        this.content = content;
        this.startAt = startAt;
    }
}
