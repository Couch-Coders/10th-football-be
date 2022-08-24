package couch.football.domain.match;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long id;

    //경기 인원
    private int matchNum;

    //현재 신청자 수
    private int applicantNum;

    @Enumerated(STRING)
    private MatchStatus status;

    @Enumerated(STRING)
    private MatchGender gender;

    @Lob
    private String content;

    //경기 시작 시간
    private LocalDateTime startAt;

    private LocalDateTime createAt;

}
