package couch.football.repository.stadium;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import couch.football.domain.stadium.Stadium;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static couch.football.domain.stadium.QStadium.stadium;

@RequiredArgsConstructor
public class StadiumRepositoryImpl implements StadiumRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Stadium> findAllBySearchOption(String address) {
        return queryFactory.selectFrom(stadium)
                .where(eqAddress(address))
                .fetch();
    }

    private BooleanExpression eqAddress(String address) {
        if (address == null || address.isEmpty()) {
            return null;
        }

        return stadium.address.contains(address);
    }
}
