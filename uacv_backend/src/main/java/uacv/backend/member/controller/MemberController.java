package uacv.backend.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uacv.backend.member.domain.Member;
import uacv.backend.member.domain.MemberAuthorizationUtil;
import uacv.backend.member.domain.Response;
import uacv.backend.member.dto.*;
import uacv.backend.member.security.jwt.TokenInfo;
import uacv.backend.member.service.MemberService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    //== 회원가입 ==//
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signup")
    public Response<?> register(@RequestBody SignupDto signupDto) {
        return new Response<>(memberService.signUp(signupDto));
    }

    //== 로그인 ==//
    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String username = memberLoginRequestDto.getUsername();
        String password = memberLoginRequestDto.getPassword();

        Member userInfo = memberService.findUser(username);
        String DbPassword = userInfo.getPassword();
        TokenInfo tokenInfo = null;

        if (passwordEncoder.matches(password, DbPassword)) {
            tokenInfo = memberService.login(username, DbPassword);
        }

        return tokenInfo;
    }

    //== 회원삭제 ==//

    //== 회원 리스트 출력 ==//
    @GetMapping("/memberList")
    public List<MemberDto> getMemberList() {
        return memberService.memberList();
    }

    //== 회원정보수정 ==//
    // 1. 비밀번호 변경
    @PutMapping("/updatePassword")
    public String updatePassword(@RequestBody UpdatePasswordDto updatePassword) {
        String username = MemberAuthorizationUtil.getLoginUsername();
        memberService.updatePassword(username, updatePassword.getCurrentPassword(), updatePassword.getNewPassword());

        return "비밀번호 수정 완료";
    }

    // 2. 권한 변경
    @PutMapping("updateRole")
    public String updateRole(@RequestBody UpdateRoleDto updateRole) {
        memberService.updateRole(updateRole.getUsername(), updateRole.getMemberRole());

        return "권한 수정 완료";
    }

    //== 권한 확인 ==// -> 삭제 예정
    @GetMapping("/admin")
    public String admin() {
        return "ADMIN 권한이다 이자식아";
    }

    @PostMapping("/control")
    public String control() {
        return "CONTROL 권한이다 이자식아";
    }

    @PostMapping("/monitor")
    public String monitor() {
        return "MONITOR 권한이다 이자식아";
    }
}
