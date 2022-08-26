package couch.football.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class TestDTO {

    private Long id;

    private int matchNum;

    private int applicantNum;

    private String content;

    @Builder
    public TestDTO(Long id, int matchNum, int applicantNum, String content) {
        this.id = id;
        this.matchNum = matchNum;
        this.applicantNum = applicantNum;
        this.content = content;
    }
}
