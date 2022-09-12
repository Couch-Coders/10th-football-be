package couch.football.controller.match;

import couch.football.domain.member.Member;
import couch.football.request.members.ReviewRequestDto;
import couch.football.response.match.ReviewResponseDto;
import couch.football.service.match.ReviewService;
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

        reviewService.create(member, requestDto);

        return ResponseEntity.ok("작성 완료");
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReviews(@PathVariable Long reviewId) {

        reviewService.delete(reviewId);

        return ResponseEntity.ok("삭제 완료");
    }

}
