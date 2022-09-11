package couch.football.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "members")
public class Member implements UserDetails, Persistable<String> {

    @Id
    private String uid;

    private String username;

    private String email;

    private String phone;

    private String gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String uid, String username, String email, String phone, String gender, Role role) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.role = role;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
