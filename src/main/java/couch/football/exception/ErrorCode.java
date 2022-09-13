package couch.football.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    BAD_REQUEST_PARAM(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    //match
    EXIST_APPLY(HttpStatus.BAD_REQUEST, "이미 신청한 경기입니다."),
    CLOSE_MATCH(HttpStatus.BAD_REQUEST, "신청 마감한 경기입니다."),
    DIFFER_GENDER(HttpStatus.BAD_REQUEST, "성별이 다른 경기입니다."),
    NOT_FOUND_MATCH(HttpStatus.NOT_FOUND, "해당 경기를 찾을 수 없습니다."),

    //stadium
    NOT_FOUND_STADIUM(HttpStatus.NOT_FOUND, "해당 경기장을 찾을 수 없습니다."),

    //like
    EXIST_LIKE(HttpStatus.BAD_REQUEST, "이미 좋아요 누른 경기장입니다."),

    //member
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    EXIST_MEMBER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),

    //review
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "해당 리뷰를 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String detail;

}
