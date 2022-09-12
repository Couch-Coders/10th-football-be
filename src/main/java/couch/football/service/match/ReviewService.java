package couch.football.service.match;

import couch.football.domain.match.Match;
import couch.football.domain.match.Review;
import couch.football.domain.member.Member;
import couch.football.exception.CustomException;
import couch.football.exception.ErrorCode;
import couch.football.repository.match.MatchRepository;
import couch.football.repository.match.ReviewRepository;
import couch.football.request.members.ReviewRequestDto;
import couch.football.response.match.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MatchRepository matchRepository;

    @Transactional
    public void create(Member member, ReviewRequestDto requestDto) {
        Match match = findMatch(requestDto.getMatchId());

        Review review = Review.builder()
                .content(requestDto.getContent())
                .match(match)
                .member(member)
                .build();

        reviewRepository.save(review);

    }

    @Transactional
    public void delete(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public ReviewResponseDto get(Member member, Long matchId) {
        Review review = reviewRepository.findByUidAndMatchId(member.getUid(), matchId);

        if (review == null) {
            Match match = findMatch(matchId);

            review = Review.builder()
                    .match(match)
                    .member(member)
                    .build();
        }

        return new ReviewResponseDto(review);

    }

    private Match findMatch(Long matchId) {
        return matchRepository.findById(matchId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MATCH));
    }
}
