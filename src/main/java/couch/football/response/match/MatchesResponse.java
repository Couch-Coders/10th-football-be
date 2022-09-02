package couch.football.response.match;

import couch.football.domain.match.Match;
import couch.football.domain.stadium.Stadium;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MatchesResponse {

    private Long id;
    private Stadium stadium;
    private Integer matchNum;
    private Integer applicantNum;
    private String status;
    private String gender;
    private String content;
    private LocalDateTime startAt;
    private Integer rest;

    public MatchesResponse(Match match) {
        this.id = match.getId();
        this.stadium = match.getStadium();
        this.matchNum = match.getMatchNum();
        this.applicantNum = match.getApplicantNum();
        this.status = match.getStatus().toString();
        this.gender = match.getGender().toString();
        this.content = match.getContent();
        this.startAt = match.getStartAt();
        this.rest = match.getRest();
    }
}
