package couch.football.repository.match;

import com.querydsl.jpa.impl.JPAQueryFactory;
import couch.football.domain.match.Match;
import couch.football.domain.match.QMatch;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MatchRepositoryImpl implements MatchRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Match> getList(int page) {
        return jpaQueryFactory.selectFrom(QMatch.match)
                .limit(10)
                .offset((long) (page - 1) * 10)
                .fetch();
    }
}
