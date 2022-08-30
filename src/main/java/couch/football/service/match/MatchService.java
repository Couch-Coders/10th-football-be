package couch.football.service.match;

import couch.football.domain.match.*;
import couch.football.domain.stadium.Stadium;
import couch.football.repository.match.MatchRepository;
import couch.football.repository.stadium.StadiumRepository;
import couch.football.request.match.MatchRequest;
import couch.football.response.match.MatchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final StadiumRepository stadiumRepository;

    //경기 생성
    public void create(MatchRequest matchRequest) {
        //경기장 조회
        Stadium stadium = stadiumRepository.findById(matchRequest.getStadiumId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경기장입니다."));

        Match match = Match.builder()
                .stadium(stadium)
                .matchNum(matchRequest.getMatchNum())
                .content(matchRequest.getContent())
                .gender(MatchGender.valueOf(matchRequest.getMatchGender().toUpperCase()))
                .startAt(matchRequest.getStartAt())
                .build();

        matchRepository.save(match);
    }

    //경기 수정
    @Transactional
    public void update(Long matchId, MatchRequest matchRequest) {
        //경기장 조회
        Stadium stadium = stadiumRepository.findById(matchRequest.getStadiumId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경기장입니다."));

        //경기 조희
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경기입니다."));


        MatchEditor.MatchEditorBuilder editorBuilder = match.toEditor();

        MatchEditor matchEditor = editorBuilder.stadium(stadium)
                .matchNum(matchRequest.getMatchNum())
                .startAt(matchRequest.getStartAt())
                .content(matchRequest.getContent())
                .gender(MatchGender.valueOf(matchRequest.getMatchGender().toUpperCase()))
                .build();

        match.edit(matchEditor);
    }

    //경기 삭제
    public void delete(Long matchId) {
        //경기 조희
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경기입니다."));

        matchRepository.delete(match);
    }


    //경기 상세 조회
    public MatchResponse get(Long matchId) {

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경기입니다."));

        Stadium stadium = stadiumRepository.findById(match.getStadium().getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경기장입니다."));

        return MatchResponse.builder()
                .id(match.getId())
                .stadium(stadium)
                .matchNum(match.getMatchNum())
                .applicantNum(match.getApplicantNum())
                .content(match.getContent())
                .status(match.getStatus().toString())
                .gender(match.getGender().toString())
                .startAt(match.getStartAt())
                .rest(match.getRest())
                .build();

    }


}
