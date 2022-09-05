package couch.football.domain.match;

import couch.football.domain.base.BaseTimeEntity;
import couch.football.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@ToString
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "reviews")
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "uid")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @Lob
    private String content;

    @Builder
    public Review(Long id, Member member, Match match, String content) {
        this.id = id;
        this.member = member;
        this.match = match;
        this.content = content;
    }
}
