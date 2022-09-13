package couch.football.service.stadium;

import couch.football.domain.member.Member;
import couch.football.domain.stadium.Like;
import couch.football.domain.stadium.Stadium;
import couch.football.exception.CustomException;
import couch.football.exception.ErrorCode;
import couch.football.repository.stadium.LikeRepository;
import couch.football.repository.stadium.StadiumRepository;
import couch.football.response.stadium.LikeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LikeService {

    private final LikeRepository likeRepository;
    private final StadiumRepository stadiumRepository;

    @Transactional
    public LikeResponse applyLike(Long stadiumId, Member member) {
        Stadium stadium = findStadium(stadiumId);

        validateApplyLike(stadiumId, member.getUid());

        Like like = Like.builder()
                .stadium(stadium)
                .member(member)
                .build();
        likeRepository.save(like);

        stadium.increaseLikeCount();

        return LikeResponse.builder()
                .stadiumId(stadiumId)
                .uid(member.getUid())
                .likeCount(stadium.getLikeCount())
                .build();

    }

    @Transactional
    public LikeResponse cancelLike(Long stadiumId, Member member) {
        Stadium stadium = findStadium(stadiumId);

        List<Like> likes = likeRepository.findAllByUidAndStadiumId(member.getUid(), stadiumId);

        if (!likes.isEmpty()) {
            likeRepository.deleteAll(likes);
        }

        stadium.decreaseLikeCount();

        return LikeResponse.builder()
                .stadiumId(stadiumId)
                .uid(member.getUid())
                .likeCount(stadium.getLikeCount())
                .build();

    }

    private Stadium findStadium(Long stadiumId) {
        return stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STADIUM));
    }

    private void validateApplyLike(Long stadiumId, String uid) {
        List<Like> likes = likeRepository.findAllByUidAndStadiumId(uid, stadiumId);

        if (!likes.isEmpty()) {
            throw new CustomException(ErrorCode.EXIST_LIKE);
        }
    }

}
