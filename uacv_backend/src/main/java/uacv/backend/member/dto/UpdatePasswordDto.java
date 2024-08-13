package uacv.backend.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordDto {
    @NotBlank
    private String newPassword;
}
