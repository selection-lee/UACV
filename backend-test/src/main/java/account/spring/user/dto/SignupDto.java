package account.spring.user.dto;

import account.spring.user.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {
    //== 회원 가입시 사용하는 DTO ==//
    private String username;
    private String password1;
    private String password2;
    private MemberRole memberRole;
}
