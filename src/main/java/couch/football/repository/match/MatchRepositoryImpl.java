package couch.football.repository.match;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import couch.football.domain.match.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static couch.football.domain.match.QMatch.match;
import static couch.football.domain.stadium.QStadium.stadium;

@RequiredArgsConstructor
public class MatchRepositoryImpl implements MatchRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Match> findAllBySearchOption(Pageable pageable, LocalDate matchDay, String gender, String status, Integer personnel, String stadiumName) {
        List<Match> matches = queryFactory.selectFrom(match)
                .where(eqStartAt(matchDay), eqGender(gender), eqStatus(status), eqPersonnel(personnel), eqStadiumName(stadiumName))
                .join(match.stadium, stadium).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(match.startAt.asc())
                .fetch();

        Long total = queryFactory.select(match.count())
                .from(match)
                .where(eqStartAt(matchDay), eqGender(gender), eqStatus(status), eqPersonnel(personnel))
                .fetchOne();

        return new PageImpl<>(matches, pageable, total);
    }

    @Override
    public Optional<Match> findByIdWithFetchJoinStadium(Long matchId) {
        Match match = queryFactory.selectFrom(QMatch.match)
                .join(QMatch.match.stadium, stadium).fetchJoin()
                .where(QMatch.match.id.eq(matchId))
                .fetchOne();

        return Optional.ofNullable(match);
    }

    private BooleanExpression eqStadiumName(String stadiumName) {
        if (stadiumName == null || stadiumName.isEmpty()) {
            return null;
        }
        return match.stadium.name.eq(stadiumName);
    }

    private BooleanExpression eqStartAt(LocalDate matchDay) {
        if (matchDay == null) {
            return null;
        }
        return match.matchDay.eq(matchDay.getDayOfYear());
    }

    private BooleanExpression eqGender(String gender) {
        if (gender == null || gender.isEmpty()) {
            return null;
        }
        return match.gender.eq(MatchGender.valueOf(gender.toUpperCase()));
    }

    private BooleanExpression eqStatus(String status) {
        if (status == null || status.isEmpty()) {
            return null;
        }
        return match.status.eq(MatchStatus.valueOf(status.toUpperCase()));
    }

    private BooleanExpression eqPersonnel(Integer personnel) {
        if (personnel == null) {
            return null;
        }
        return match.matchNum.eq(personnel);
    }


}
