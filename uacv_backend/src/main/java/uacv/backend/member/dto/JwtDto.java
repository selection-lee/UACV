package uacv.backend.member.dto;

public record JwtDto(
        String accessToken,
        String refreshToken
) {
}
