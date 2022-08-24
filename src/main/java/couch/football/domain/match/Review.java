package couch.football.domain.match;

import couch.football.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "reviews")
public class Review {

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

    private LocalDateTime createAt;

    @Builder
    public Review(Member member, Match match, String content) {
        this.member = member;
        this.match = match;
        this.content = content;
        this.createAt = LocalDateTime.now();
    }
}
