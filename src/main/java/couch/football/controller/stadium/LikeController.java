package couch.football.controller.stadium;

import couch.football.domain.member.Member;
import couch.football.response.stadium.LikeResponse;
import couch.football.service.stadium.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{stadiumId}")
    public LikeResponse applyLike(@PathVariable Long stadiumId, Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();

        return likeService.applyLike(stadiumId, member);
    }

    @DeleteMapping("/{stadiumId}")
    public LikeResponse cancelLike(@PathVariable Long stadiumId, Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();

        return likeService.cancelLike(stadiumId, member);
    }
}
