package couch.football.service.match;

import couch.football.domain.match.*;
import couch.football.domain.member.Member;
import couch.football.repository.match.MatchRepository;
import couch.football.repository.match.ApplicationRepository;
import couch.football.response.match.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final MatchRepository matchRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public ApplicationResponse applyMatch(Long matchId, Member member) {
        Match match = findMatch(matchId);

        if (!match.getGender().equals(MatchGender.valueOf(member.getGender().toUpperCase()))
                && !match.getGender().equals(MatchGender.ALL)) {
            throw new IllegalArgumentException("경기 성별을 확인해주세요.");
        }

        if (match.getStatus().equals(MatchStatus.CLOSE)) {
            throw new IllegalArgumentException("마감 된 경기입니다.");
        }

        Application application = Application.builder()
                .match(match)
                .member(member)
                .build();
        applicationRepository.save(application);

        match.increaseApplicantNum();

        match.updateStatus();

        return ApplicationResponse.builder()
                .id(matchId)
                .rest(match.getRest())
                .status(match.getStatus().toString())
                .application(application)
                .build();
    }

    @Transactional
    public ApplicationResponse cancelMatch(Long matchId, Member member) {
        Match match = findMatch(matchId);

        List<Application> applications = applicationRepository.findAllByUidAndMatchId(member.getUid(), matchId);

        if (!applications.isEmpty()) {
            applicationRepository.deleteAll(applications);
        }

        match.decreaseApplicantNum();

        match.updateStatus();

        return ApplicationResponse.builder()
                .id(matchId)
                .rest(match.getRest()) //남은 자리 수
                .status(match.getStatus().toString())
                .build();
    }

    public List<Application> getList(Long matchId) {
        return applicationRepository.findByMatchId(matchId);
    }

    private Match findMatch(Long matchId) {
        return matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경기입니다."));
    }

}
