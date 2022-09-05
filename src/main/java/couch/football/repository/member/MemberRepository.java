package couch.football.repository.member;

import couch.football.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByUid(String uid);

    boolean existsByUid(String uid);

}
