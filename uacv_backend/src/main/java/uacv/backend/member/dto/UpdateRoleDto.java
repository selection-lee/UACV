package uacv.backend.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import uacv.backend.member.domain.MemberRole;

@Data
public class UpdateRoleDto {
    @NotBlank
    private MemberRole memberRole;
}
