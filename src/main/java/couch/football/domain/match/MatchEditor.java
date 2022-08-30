package couch.football.domain.match;

import couch.football.domain.stadium.Stadium;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MatchEditor {

    private final Stadium stadium;
    private final Integer matchNum;
    private final LocalDateTime startAt;
    private final MatchGender gender;
    private final String content;

    @Builder
    public MatchEditor(Stadium stadium, Integer matchNum, LocalDateTime startAt, MatchGender gender, String content) {
        this.stadium = stadium;
        this.matchNum = matchNum;
        this.startAt = startAt;
        this.gender = gender;
        this.content = content;
    }
}
