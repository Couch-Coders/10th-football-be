package couch.football.response.match;

import couch.football.domain.match.Application;
import couch.football.response.members.MemberResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationResponse {

    private Long matchId;
    private Integer rest;
    private String status;
    private Long applicationId;
    private MemberResponseDto applicant;

    @Builder
    public ApplicationResponse(Long matchId, Integer rest, String status, Long applicationId, MemberResponseDto applicant) {
        this.matchId = matchId;
        this.rest = rest;
        this.status = status;
        this.applicationId = applicationId;
        this.applicant = applicant;
    }
}
