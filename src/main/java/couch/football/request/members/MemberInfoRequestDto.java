package couch.football.request.members;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class MemberInfoRequestDto {

    @NotNull(message = "성별은 선택해 주세요")
    @NotBlank(message = "성별을 선택해 주세요")
    private String gender;

    @NotNull
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$",message = "전화번호를 알맞게 입력해 주세요")
    private String phone;

}
