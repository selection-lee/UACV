package uacv.backend.member.dto;

import lombok.Data;
import uacv.backend.member.domain.MemberRole;

import java.time.LocalDateTime;

@Data
public class MemberDto {

    private Long id;

    private String username;

    private MemberRole memberRole;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
