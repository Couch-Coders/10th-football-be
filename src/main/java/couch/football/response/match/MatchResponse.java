package couch.football.response.match;

import couch.football.domain.match.Match;
import couch.football.response.stadium.StadiumResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MatchResponse {

    private Long id;
    private StadiumResponse stadium;
    private Integer matchNum;
    private Integer applicantNum;
    private String status;
    private String gender;
    private String content;
    private LocalDateTime startAt;
    private Integer rest;

    public MatchResponse(Match match) {
        this.id = match.getId();
        this.stadium = StadiumResponse.mapToDto(match.getStadium());
        this.matchNum = match.getMatchNum();
        this.applicantNum = match.getApplicantNum();
        this.status = match.getStatus().toString();
        this.gender = match.getGender().toString();
        this.content = match.getContent();
        this.startAt = match.getStartAt();
        this.rest = match.getRest();
    }

}
