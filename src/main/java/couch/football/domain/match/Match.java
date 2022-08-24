package couch.football.domain.match;

import couch.football.domain.stadium.Stadium;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "match_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @OneToMany(mappedBy = "match")
    private List<Review> reviews = new ArrayList<>();

    private int matchNum;

    private int applicantNum;

    @Enumerated(STRING)
    private MatchStatus status;

    @Enumerated(STRING)
    private MatchGender gender;

    @Lob
    private String content;

    private LocalDateTime startAt;

    private LocalDateTime createAt;

    @Builder
    public Match(Stadium stadium, int matchNum, int applicantNum, MatchStatus status, MatchGender gender, String content, LocalDateTime startAt) {
        this.stadium = stadium;
        this.matchNum = matchNum;
        this.applicantNum = applicantNum;
        this.status = status;
        this.gender = gender;
        this.content = content;
        this.startAt = startAt;
        this.createAt = LocalDateTime.now();
    }
}
