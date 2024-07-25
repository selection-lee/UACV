package account.spring.user.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenInfo {
    //== 토큰 출력시 사용 ==//
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
