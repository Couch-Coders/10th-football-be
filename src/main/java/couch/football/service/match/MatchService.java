package couch.football.service.match;

import couch.football.domain.match.Match;
import couch.football.domain.match.MatchGender;
import couch.football.domain.match.MatchStatus;
import couch.football.domain.stadium.Stadium;
import couch.football.repository.match.MatchRepository;
import couch.football.repository.match.SubscriptionRepository;
import couch.football.repository.member.MemberRepository;
import couch.football.repository.stadium.StadiumRepository;
import couch.football.request.match.MatchCreate;
import couch.football.response.match.MatchResponse;
import couch.football.response.match.SubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final StadiumRepository stadiumRepository;
    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;

    public void createMatch(MatchCreate matchCreate) {
        //경기장 조회
        Stadium stadium = stadiumRepository.findById(matchCreate.getStadiumId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경기장입니다."));

        Match match = Match.builder()
                .stadium(stadium)
                .matchNum(matchCreate.getMatchNum())
                .applicantNum(0)
                .status(MatchStatus.OPEN)
                .content(matchCreate.getContent())
                .gender(MatchGender.valueOf(matchCreate.getMatchGender()))
                .startAt(matchCreate.getStartAt())
                .build();

        matchRepository.save(match);
    }

//    public SubscriptionResponse applyMatch(Long matchId) {
//
//    }

    public MatchResponse getMatch(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경기입니다."));

        return MatchResponse.builder()
                .id(match.getId())
                .stadium(match.getStadium())
                .matchNum(match.getMatchNum())
                .applicantNum(match.getApplicantNum())
                .status(match.getStatus().toString())
                .gender(match.getGender().toString())
                .startAt(match.getStartAt())
                .createAt(match.getCreateAt())
                .build();
    }


}
