package couch.football.service.match;

import couch.football.domain.match.*;
import couch.football.domain.member.Member;
import couch.football.domain.stadium.Stadium;
import couch.football.repository.match.MatchRepository;
import couch.football.repository.member.MemberRepository;
import couch.football.repository.stadium.StadiumRepository;
import couch.football.request.match.MatchCreateRequest;
import couch.football.request.match.MatchUpdateRequest;
import couch.football.response.match.MatchApplicantResponse;
import couch.football.response.match.MatchResponse;
import couch.football.response.match.MatchesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class MatchService {

    private final MatchRepository matchRepository;
    private final StadiumRepository stadiumRepository;
    private final MemberRepository memberRepository;

    private final ApplicationService applicationService;

    @Transactional
    public void create(MatchCreateRequest request) {
        Stadium stadium = findStadium(request.getStadiumId());

        Match match = Match.builder()
                .stadium(stadium)
                .request(request)
                .build();

        matchRepository.save(match);
    }

    @Transactional
    public void update(Long matchId, MatchUpdateRequest request) {
        Match match = findMatch(matchId);

        Stadium stadium = findStadium(request.getStadiumId());

        match.update(stadium, request);
    }

    @Transactional
    public void delete(Long matchId) {
        Match match = findMatch(matchId);

        matchRepository.delete(match);
    }

    public Page<MatchesResponse> getList(Pageable pageable, LocalDate matchDay, String gender, String status, Integer personnel, String stadiumName) {
        return matchRepository.findAllBySearchOption(pageable, matchDay, gender, status, personnel, stadiumName)
                .map(MatchesResponse::new);
    }

    public MatchResponse get(Long matchId) {
        Match match = matchRepository.findByIdWithFetchJoinStadium(matchId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경기입니다."));;

        List<MatchApplicantResponse> matchApplicants = new ArrayList<>();

        List<Application> applicants = applicationService.getList(matchId);
        for (Application application : applicants) {
            Member member = memberRepository.findByUid(application.getMember().getUid())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

            MatchApplicantResponse applicant = new MatchApplicantResponse(member);

            matchApplicants.add(applicant);
        }

        return MatchResponse.builder()
                .match(match)
                .matchApplicants(matchApplicants)
                .build();
    }

    private Match findMatch(Long matchId) {
        return matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경기입니다."));
    }

    private Stadium findStadium(Long stadiumId) {
        return stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경기장입니다."));
    }
}
