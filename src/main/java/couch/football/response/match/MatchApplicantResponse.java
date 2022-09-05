package couch.football.response.match;

import couch.football.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MatchApplicantResponse {
    private String uid;
    private String username;
    private String email;
    private String gender;
    private String phone;

    public MatchApplicantResponse(Member member) {
        this.uid = member.getUid();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.gender = member.getGender();
        this.phone = member.getPhone();
    }
}
