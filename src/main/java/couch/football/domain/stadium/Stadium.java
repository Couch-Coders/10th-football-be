package couch.football.domain.stadium;

import couch.football.domain.match.Match;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "stadiums")
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stadium_id")
    private Long id;

    @OneToMany(mappedBy = "matches", fetch = FetchType.LAZY)
    private List<Match> matches = new ArrayList<>();

    @OneToMany(mappedBy = "files", fetch = FetchType.LAZY)
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "likes", fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private boolean parking;

    @Column(nullable = true)
    private boolean rental;

    @Column(nullable = false)
    private String address;

    @Column(name = "like_count", nullable = true)
    private Long likeCount;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

}
