package couch.football.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "members")
public class Member {

    @Id
    private String uid;

    private String username;

    private String email;

    private String gender;

    private String role;

    private String phone;

    @Builder
    public Member(String uid, String username, String email, String gender, String role, String phone) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.role = role;
        this.phone = phone;
    }
}
