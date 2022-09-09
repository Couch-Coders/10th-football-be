package couch.football.domain.stadium;

import com.fasterxml.jackson.annotation.JsonIgnore;
import couch.football.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "stadiums")
public class Stadium extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stadium_id")
    private Long id;

    @OneToMany(mappedBy = "stadium", cascade = REMOVE, orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    private String name;

    @Lob
    private String content;

    private Boolean parking;

    private Boolean rental;

    private String address;

    private Long likeCount;

    @OneToMany(mappedBy = "stadium", cascade = REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<Like> likes = new ArrayList<>();

    @Builder
    public Stadium(Long id, List<File> files, String name, String content, boolean parking, boolean rental, String address, Long likeCount) {
        this.id = id;
        this.files = files;
        this.name = name;
        this.content = content;
        this.parking = parking;
        this.rental = rental;
        this.address = address;
        this.likeCount = likeCount;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }
}
