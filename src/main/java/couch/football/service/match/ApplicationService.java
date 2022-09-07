package couch.football.service.match;

import couch.football.domain.match.*;
import couch.football.domain.member.Member;
import couch.football.exception.CustomException;
import couch.football.exception.ErrorCode;
import couch.football.repository.match.MatchRepository;
import couch.football.repository.match.ApplicationRepository;
import couch.football.repository.member.MemberRepository;
import couch.football.response.match.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {

    private final MatchRepository matchRepository;
    private final ApplicationRepository applicationRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public ApplicationResponse applyMatch(Long matchId, Member member) {
        Match match = findMatch(matchId);

        validateApplyMatch(match, member);

        Application application = Application.builder()
                .match(match)
                .member(member)
                .build();
        applicationRepository.save(application);

        match.increaseApplicantNum();

        match.updateStatus();

        return ApplicationResponse.builder()
                .matchId(matchId)
                .rest(match.getRest())
                .status(match.getStatus().toString())
                .applicationId(application.getId())
                .applicant(new MatchApplicantResponse(member))
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
                .matchId(matchId)
                .rest(match.getRest())
                .status(match.getStatus().toString())
                .build();
    }

    public Page<ApplicationListResponse> getApplications(Pageable pageable, Member member) {
        return applicationRepository.findAllByUid(pageable, member.getUid()).map(ApplicationListResponse::new);
    }

    public List<Application> getList(Long matchId) {
        return applicationRepository.findByMatchId(matchId);
    }

    private Match findMatch(Long matchId) {
        return matchRepository.findByIdWithFetchJoinStadium(matchId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MATCH));
    }

    private void validateApplyMatch(Match match, Member member) {
        if (!match.getGender().equals(MatchGender.valueOf(member.getGender().toUpperCase()))
                && !match.getGender().equals(MatchGender.ALL)) {
            throw new CustomException(ErrorCode.DIFFER_GENDER);
        }

        if (match.getStatus().equals(MatchStatus.CLOSE)) {
            throw new CustomException(ErrorCode.CLOSE_MATCH);
        }

        List<Application> applications = applicationRepository.findAllByUidAndMatchId(member.getUid(), match.getId());
        if (!applications.isEmpty()) {
            throw new CustomException(ErrorCode.EXIST_APPLY);
        }
    }

}
