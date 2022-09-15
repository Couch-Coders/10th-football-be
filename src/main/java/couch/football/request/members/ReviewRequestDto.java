package couch.football.request.members;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class ReviewRequestDto {
    private Long matchId;

    @NotNull
    @Size(min=3, message = "리뷰를 입력해 주세요(최소 3글자)")
    private String content;
}
