package couch.football.repository.match;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import couch.football.domain.match.Review;
import lombok.RequiredArgsConstructor;

import static couch.football.domain.match.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Review findByUidAndMatchId(String uid, Long matchId) {
        return queryFactory.selectFrom(review)
                .where(eqUid(uid), eqMatchId(matchId))
                .fetchOne();
    }

    private BooleanExpression eqUid(String uid) {
        if (uid == null || uid.isEmpty()) {
            return null;
        }
        return review.member.uid.eq(uid);
    }

    private BooleanExpression eqMatchId(Long matchId) {
        if (matchId == null) {
            return null;
        }
        return review.match.id.eq(matchId);
    }
}
