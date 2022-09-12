package couch.football.controller.member;

import couch.football.domain.member.Member;
import couch.football.request.members.ReviewRequestDto;
import couch.football.service.member.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity createReviews(Authentication authentication,
                                                @RequestBody ReviewRequestDto requestDto) {
        Member member = (Member) authentication.getPrincipal();

        reviewService.saveReview(member, requestDto);

        return ResponseEntity.ok("작성 완료");
    }
}
