package uacv.backend.member.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uacv.backend.member.domain.MemberRole;

@Builder
@Data
@AllArgsConstructor
public class TokenInfo {
    //== 토큰 출력시 사용 ==//
    private MemberRole memberRole;
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
