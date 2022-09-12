package couch.football.controller.member;

import couch.football.domain.member.Member;
import couch.football.repository.member.ReviewRepository;
import couch.football.request.members.ReviewRequestDto;
import couch.football.response.members.ReviewResponseDto;
import couch.football.service.member.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<String> createReviews(Authentication authentication,
                                                @RequestBody ReviewRequestDto requestDto) {
        Member member = (Member) authentication.getPrincipal();

        reviewService.saveReview(member, requestDto);

        return ResponseEntity.ok("작성 완료");
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReviews(@PathVariable Long reviewId) {

        reviewService.deleteReview(reviewId);

        return ResponseEntity.ok("삭제 완료");
    }

    @GetMapping("/{reviewId}")
    public ReviewResponseDto findOneReview(@PathVariable Long reviewId) {


        return reviewService.findOneReview(reviewId);
    }
}
