package couch.football.repository.match;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import couch.football.domain.match.Application;
import couch.football.domain.match.QApplication;
import couch.football.domain.match.QMatch;
import couch.football.domain.member.QMember;
import couch.football.domain.stadium.QFile;
import couch.football.domain.stadium.QStadium;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static couch.football.domain.match.QApplication.application;
import static couch.football.domain.match.QMatch.match;
import static couch.football.domain.stadium.QStadium.stadium;


@RequiredArgsConstructor
public class ApplicationRepositoryImpl implements ApplicationRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Application findByUidAndMatchId(String uid, Long matchId) {

        return queryFactory.selectFrom(QApplication.application)
                .where(eqUid(uid), eqMatchId(matchId))
                .fetchOne();

    }

    @Override
    public Page<Application> findAllByUid(Pageable pageable, String uid) {
        List<Application> applications = queryFactory.selectFrom(application)
                .where(eqUid(uid))
                .join(application.match, match).fetchJoin()
                .join(match.stadium, stadium).fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(application.match.startAt.desc())
                .fetch();

        Long total = queryFactory.select(application.count())
                .from(application)
                .where(eqUid(uid))
                .fetchOne();

        return new PageImpl<>(applications, pageable, total);
    }


    private BooleanExpression eqUid(String uid) {
        if (uid == null || uid.isEmpty()) {
            return null;
        }
        return application.member.uid.eq(uid);
    }

    private BooleanExpression eqMatchId(Long matchId) {
        if (matchId == null) {
            return null;
        }
        return application.match.id.eq(matchId);
    }
}
