package couch.football.service.match;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import couch.football.domain.match.*;
import couch.football.domain.member.Member;
import couch.football.domain.stadium.Stadium;
import couch.football.exception.CustomException;
import couch.football.exception.ErrorCode;
import couch.football.repository.match.MatchRepository;
import couch.football.repository.member.MemberRepository;
import couch.football.repository.match.ReviewRepository;
import couch.football.repository.stadium.LikeRepository;
import couch.football.repository.stadium.StadiumRepository;
import couch.football.request.match.MatchCreateRequest;
import couch.football.request.match.MatchUpdateRequest;
import couch.football.response.match.MatchDetailResponse;
import couch.football.response.match.MatchResponse;
import couch.football.response.members.MemberResponseDto;
import couch.football.response.match.ReviewResponseDto;
import couch.football.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    private final StadiumRepository stadiumRepository;
    private final MatchRepository matchRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;

    private final MemberService memberService;
    private final ApplicationService applicationService;

    private final FirebaseAuth firebaseAuth;

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

    @Transactional
    public Page<MatchResponse> getList(Pageable pageable, LocalDate matchDay, String gender, String status, Integer personnel, String stadiumName) {
        Page<Match> matches = matchRepository.findAllBySearchOption(pageable, matchDay, gender, status, personnel, stadiumName);
        for (Match match : matches) {
            match.updateStatus();
        }

        return matches.map(MatchResponse::new);
    }

    public MatchDetailResponse get(Long matchId, String header) {

        Match match = matchRepository.findByIdWithFetchJoinStadium(matchId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MATCH));

        List<ReviewResponseDto> matchReviews = reviewRepository.findByMatchId(matchId).stream()
                .map(ReviewResponseDto::new).collect(Collectors.toList());

        List<MemberResponseDto> matchApplicants = new ArrayList<>();
        Boolean applyStatus = null;
        Boolean likeStatus = null;
        if (header == null) {
            return MatchDetailResponse.builder()
                    .match(match)
                    .applyStatus(applyStatus)
                    .likeStatus(likeStatus)
                    .matchReviews(matchReviews)
                    .build();
        } else {
            Member member = getMemberOrElseThrow(header);

            for (Application application : applicationService.getList(matchId)) {
                Member findMember = memberRepository.findByUid(application.getMember().getUid())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));

                if (findMember.getUid().equals(member.getUid())) {
                    applyStatus = true;
                }

                matchApplicants.add(new MemberResponseDto(findMember));
            }

            if(!likeRepository.findAllByUidAndStadiumId(member.getUid(), match.getStadium().getId()).isEmpty()) {
                likeStatus = true;
            }
        }

        return MatchDetailResponse.builder()
                .match(match)
                .applyStatus(applyStatus)
                .likeStatus(likeStatus)
                .matchApplicants(matchApplicants)
                .matchReviews(matchReviews)
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

    private Member getMemberOrElseThrow(String header) {
        Member member;
        try {
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(header);
            member = (Member) memberService.loadUserByUsername(decodedToken.getUid());
        } catch (UsernameNotFoundException | FirebaseAuthException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_MEMBER, "토큰에 해당하는 회원이 존재하지 않습니다.");
        }
        return member;
    }
}
