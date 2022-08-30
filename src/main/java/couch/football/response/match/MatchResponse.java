package couch.football.response.match;

import couch.football.domain.match.Match;
import couch.football.domain.stadium.Stadium;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MatchResponse {

    private final Long id;
    private final Stadium stadium;
    private final Integer matchNum;
    private final Integer applicantNum;
    private final String status;
    private final String gender;
    private final String content;
    private final LocalDateTime startAt;
    private final Integer rest;

    public MatchResponse(Match match) {
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

    @Builder

    public MatchResponse(Long id, Stadium stadium, Integer matchNum, Integer applicantNum,
                         String status, String gender, String content, LocalDateTime startAt, Integer rest) {
        this.id = id;
        this.stadium = stadium;
        this.matchNum = matchNum;
        this.applicantNum = applicantNum;
        this.status = status;
        this.gender = gender;
        this.content = content;
        this.startAt = startAt;
        this.rest = rest;
    }
}
