package couch.football.domain.stadium;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStadium is a Querydsl query type for Stadium
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStadium extends EntityPathBase<Stadium> {

    private static final long serialVersionUID = -511450333L;

    public static final QStadium stadium = new QStadium("stadium");

    public final couch.football.domain.base.QBaseTimeEntity _super = new couch.football.domain.base.QBaseTimeEntity(this);

    public final StringPath address = createString("address");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<File, QFile> files = this.<File, QFile>createList("files", File.class, QFile.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Long> likeCount = createNumber("likeCount", Long.class);

    public final StringPath name = createString("name");

    public final BooleanPath parking = createBoolean("parking");

    public final BooleanPath rental = createBoolean("rental");

    public QStadium(String variable) {
        super(Stadium.class, forVariable(variable));
    }

    public QStadium(Path<? extends Stadium> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStadium(PathMetadata metadata) {
        super(Stadium.class, metadata);
    }

}

