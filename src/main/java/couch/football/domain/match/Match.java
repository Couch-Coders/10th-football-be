package couch.football.domain.match;

import couch.football.domain.base.BaseTimeEntity;
import couch.football.domain.stadium.Stadium;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "matches")
public class Match extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "match_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @OneToMany(mappedBy = "match")
    private List<Review> reviews = new ArrayList<>();

    private Integer matchNum;

    private Integer applicantNum;

    @Enumerated(STRING)
    private MatchStatus status;

    @Enumerated(STRING)
    private MatchGender gender;

    @Lob
    private String content;

    private LocalDateTime startAt;

    @Builder
    public Match(Stadium stadium, Integer matchNum, Integer applicantNum,
                 MatchStatus status, MatchGender gender, String content, LocalDateTime startAt) {
        this.stadium = stadium;
        this.matchNum = matchNum;
        this.applicantNum = applicantNum;
        this.status = status;
        this.gender = gender;
        this.content = content;
        this.startAt = startAt;
    }

    public MatchEditor.MatchEditorBuilder toEditor() {
        return MatchEditor.builder()
                .stadium(stadium)
                .matchNum(matchNum)
                .applicantNum(applicantNum)
                .content(content)
                .gender(gender)
                .status(status)
                .startAt(startAt);
    }

    public void edit(MatchEditor matchEditor) {
        stadium = matchEditor.getStadium();
        matchNum = matchEditor.getMatchNum();
        applicantNum = matchEditor.getApplicantNum();
        content = matchEditor.getContent();
        gender = matchEditor.getGender();
        status = matchEditor.getStatus();
        startAt = matchEditor.getStartAt();
    }

    public void increaseApplicantNum() {
        this.applicantNum++;
    }

    public void decreaseApplicantNum() {
        this.applicantNum--;
    }

    public int getRest() {
        return this.matchNum - this.applicantNum;
    }

    public void updateStatus() {
        if (getRest() <= 0) {
            this.status = MatchStatus.CLOSE;
        } else if (getRest() > 0) {
            this.status = MatchStatus.OPEN;
        }
    }
}
