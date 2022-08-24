package couch.football.domain.stadium;

import lombok.AccessLevel;
import lombok.Builder;
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

    @OneToMany(mappedBy = "stadium")
    private List<File> files = new ArrayList<>();

    private String name;

    @Lob
    private String content;

    private boolean parking;

    private boolean rental;

    private String address;

    private Long likeCount;

    private LocalDateTime createAt;

    @Builder

    public Stadium(List<File> files, String name, String content, boolean parking, boolean rental, String address, Long likeCount) {
        this.files = files;
        this.name = name;
        this.content = content;
        this.parking = parking;
        this.rental = rental;
        this.address = address;
        this.likeCount = likeCount;
        this.createAt = LocalDateTime.now();
    }
}
