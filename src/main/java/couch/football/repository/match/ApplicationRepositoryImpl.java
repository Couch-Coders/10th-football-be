package couch.football.repository.match;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import couch.football.domain.match.Application;
import couch.football.domain.match.QApplication;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ApplicationRepositoryImpl implements ApplicationRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Application> findAllByUidAndMatchId(String uid, Long matchId) {
        List<Application> applications = queryFactory.selectFrom(QApplication.application)
                .where(eqUid(uid), eqMatchId(matchId))
                .fetch();

        return applications;
    }

    private BooleanExpression eqUid(String uid) {
        if (uid == null || uid.isEmpty()) {
            return null;
        }
        return QApplication.application.member.uid.eq(uid);
    }

    private BooleanExpression eqMatchId(Long matchId) {
        if (matchId == null) {
            return null;
        }
        return QApplication.application.match.id.eq(matchId);
    }
}
