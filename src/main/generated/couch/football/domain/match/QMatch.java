package couch.football.domain.match;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatch is a Querydsl query type for Match
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatch extends EntityPathBase<Match> {

    private static final long serialVersionUID = -1785181197L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatch match = new QMatch("match");

    public final couch.football.domain.base.QBaseTimeEntity _super = new couch.football.domain.base.QBaseTimeEntity(this);

    public final NumberPath<Integer> applicantNum = createNumber("applicantNum", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final EnumPath<MatchGender> gender = createEnum("gender", MatchGender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> matchDay = createNumber("matchDay", Integer.class);

    public final NumberPath<Integer> matchNum = createNumber("matchNum", Integer.class);

    public final couch.football.domain.stadium.QStadium stadium;

    public final DateTimePath<java.time.LocalDateTime> startAt = createDateTime("startAt", java.time.LocalDateTime.class);

    public final EnumPath<MatchStatus> status = createEnum("status", MatchStatus.class);

    public QMatch(String variable) {
        this(Match.class, forVariable(variable), INITS);
    }

    public QMatch(Path<? extends Match> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatch(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatch(PathMetadata metadata, PathInits inits) {
        this(Match.class, metadata, inits);
    }

    public QMatch(Class<? extends Match> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.stadium = inits.isInitialized("stadium") ? new couch.football.domain.stadium.QStadium(forProperty("stadium")) : null;
    }

}

