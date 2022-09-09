package couch.football.repository.stadium;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import couch.football.domain.stadium.Like;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static couch.football.domain.stadium.QLike.like;

@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Like> findAllByUidAndStadiumId(String uid, Long stadiumId) {
        List<Like> likes = queryFactory.selectFrom(like)
                .where(eqUid(uid), eqStadiumId(stadiumId))
                .fetch();

        return likes;
    }

    private BooleanExpression eqUid(String uid) {
        if (uid == null || uid.isEmpty()) {
            return null;
        }
        return like.member.uid.eq(uid);
    }

    private BooleanExpression eqStadiumId(Long stadiumId) {
        if (stadiumId == null) {
            return null;
        }
        return like.stadium.id.eq(stadiumId);
    }
}
