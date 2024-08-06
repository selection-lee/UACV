package uacv.backend.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdatePasswordDto {
    @NotBlank
    private String currentPassword;

    @NotBlank
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,16}",
            message = "비밀번호는 8-16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String newPassword;

}
