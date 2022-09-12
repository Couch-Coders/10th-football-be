package couch.football.service.member;

import couch.football.domain.match.Match;
import couch.football.domain.match.Review;
import couch.football.domain.member.Member;
import couch.football.repository.match.MatchRepository;
import couch.football.repository.member.ReviewRepository;
import couch.football.request.members.ReviewRequestDto;
import couch.football.response.members.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MatchRepository matchRepository;

    @Transactional
    public void saveReview(Member member, ReviewRequestDto requestDto) {
        Optional<Match> match = matchRepository.findById(requestDto.getMatchId());

        Review review = Review.builder()
                .content(requestDto.getContent())
                .match(match.get())
                .member(member)
                .build();

        reviewRepository.save(review);

    }

    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public ReviewResponseDto findOneReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).get();

        ReviewResponseDto responseDto = ReviewResponseDto.builder()
                .id(review.getId())
                .matchId(review.getMatch().getId())
                .uId(review.getMember().getUid())
                .content(review.getContent())
                .createdDate(review.getCreatedDate())
                .build();

        return responseDto;
    }
}
