package couch.football.service.match;

import couch.football.domain.match.*;
import couch.football.domain.member.Member;
import couch.football.domain.stadium.Stadium;
import couch.football.exception.CustomException;
import couch.football.exception.ErrorCode;
import couch.football.repository.match.MatchRepository;
import couch.football.repository.member.MemberRepository;
import couch.football.repository.stadium.StadiumRepository;
import couch.football.request.match.MatchCreateRequest;
import couch.football.request.match.MatchUpdateRequest;
import couch.football.response.match.MatchDetailResponse;
import couch.football.response.match.MatchResponse;
import couch.football.response.members.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Page<MatchResponse> getList(Pageable pageable, LocalDate matchDay, String gender, String status, Integer personnel, String stadiumName) {
        return matchRepository.findAllBySearchOption(pageable, matchDay, gender, status, personnel, stadiumName)
                .map(MatchResponse::new);
    }

    public MatchDetailResponse get(Long matchId) {
        Match match = matchRepository.findByIdWithFetchJoinStadium(matchId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MATCH));

        List<MemberResponseDto> matchApplicants = new ArrayList<>();
        for (Application application : applicationService.getList(matchId)) {
            Member member = memberRepository.findByUid(application.getMember().getUid())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));

            matchApplicants.add(new MemberResponseDto(member));
        }

        return MatchDetailResponse.builder()
                .match(match)
                .matchApplicants(matchApplicants)
                .build();
    }

    private Match findMatch(Long matchId) {
        return matchRepository.findById(matchId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MATCH));
    }

    private Stadium findStadium(Long stadiumId) {
        return stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STADIUM));
    }
}
