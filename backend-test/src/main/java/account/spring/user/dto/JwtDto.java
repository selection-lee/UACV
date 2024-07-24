package account.spring.user.dto;

public record JwtDto(
        String accessToken,
        String refreshToken
) {
}
