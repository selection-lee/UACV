package uacv.backend.member.controller;

import uacv.backend.member.domain.Member;
import uacv.backend.member.domain.MemberAuthorizationUtil;
import uacv.backend.member.domain.Response;
import uacv.backend.member.dto.MemberLoginRequestDto;
import uacv.backend.member.dto.SignupDto;
import uacv.backend.member.dto.UpdatePasswordDto;
import uacv.backend.member.security.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uacv.backend.member.service.MemberService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class MemberController {

    private final MemberService userService;
    private final PasswordEncoder passwordEncoder;

    //== 회원가입 ==//
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signup")
    public Response<?> register(@RequestBody SignupDto signupDto) {
        return new Response<>(userService.signUp(signupDto));
    }

    //== 로그인 ==//
    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String username = memberLoginRequestDto.getUsername();
        String password = memberLoginRequestDto.getPassword();

        Member userInfo = userService.findUser(username);
        String DbPassword = userInfo.getPassword();
        TokenInfo tokenInfo = null;

        if (passwordEncoder.matches(password, DbPassword)) {
            tokenInfo = userService.login(username, DbPassword);
        }

        return tokenInfo;
    }

    //== 회원정보수정 ==//
    // 1. 비밀번호 변경
    @PutMapping("/updatePassword")
    public String updatePassword(@RequestBody UpdatePasswordDto updatePassword) {
        System.out.println(updatePassword.getNewPassword());
        String username = MemberAuthorizationUtil.getLoginUsername();
        userService.updatePassword(username, updatePassword.getCurrentPassword(), updatePassword.getNewPassword());
        return "비밀번호 수정 완료";
    }
    //== 로그아웃 ==//

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
