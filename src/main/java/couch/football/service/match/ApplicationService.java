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

    //경기 신청
    @Transactional
    public ApplicationResponse applyMatch(Long matchId, Member member) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경기입니다."));

        //성별이 다르거나 ALL이 아닌 경우 신청 불가!
        if (!match.getGender().equals(MatchGender.valueOf(member.getGender().toUpperCase()))
                && !match.getGender().equals(MatchGender.ALL)) {
            throw new IllegalArgumentException("경기 성별을 확인해주세요.");
        }

        //경기 상태가 마감인 경우 신청 불가!
        if (match.getStatus().equals(MatchStatus.CLOSE)) {
            throw new IllegalArgumentException("마감 된 경기입니다.");
        }

        //신청 진행
        Application application = Application.builder()
                .match(match)
                .member(member)
                .build();
        applicationRepository.save(application);

        //신청자 수 변경
        match.increaseApplicantNum();

        //경기 상태 변경
        match.updateStatus();

        return ApplicationResponse.builder()
                .id(matchId)
                .rest(match.getRest()) //남은 자리 수
                .status(match.getStatus().toString())
                .application(application)
                .build();
    }

    //경기 신청 취소
    @Transactional
    public ApplicationResponse cancelMatch(Long matchId, Member member) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경기입니다."));

        //신청여부 조회
        List<Application> applications = applicationRepository.findAllByUidAndMatchId(member.getUid(), matchId);

        //신청 취소
        if (!applications.isEmpty()) {
            applicationRepository.deleteAll(applications);
        }

        //신청자 수 변경
        match.decreaseApplicantNum();

        //경기 상태 변경
        match.updateStatus();

        return ApplicationResponse.builder()
                .id(matchId)
                .rest(match.getRest()) //남은 자리 수
                .status(match.getStatus().toString())
                .build();
    }

    //경기 신청자 조회
    public List<Application> getList(Long matchId) {
        return applicationRepository.findByMatchId(matchId);
    }

}
