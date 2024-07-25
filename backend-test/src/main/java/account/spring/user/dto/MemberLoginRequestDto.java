package account.spring.user.dto;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
    //== 로그인 시 사용하는 DTO ==//
    private String username;
    private String password;
}
