package couch.football.response.members;

import couch.football.domain.member.Member;
import couch.football.domain.member.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private String uid;

    private String username;

    private String email;

    private String phone;

    private String gender;

    private Role role;

    @Builder
    public MemberResponseDto(Member member) {
        this.uid = member.getUid();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.gender = member.getGender();
        this.role = member.getRole();
        this.phone = member.getPhone();
    }

}
