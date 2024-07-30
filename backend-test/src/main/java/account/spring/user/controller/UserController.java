package account.spring.user.controller;

import account.spring.user.domain.Member;
import account.spring.user.domain.Response;
import account.spring.user.dto.MemberLoginRequestDto;
import account.spring.user.dto.SignupDto;
import account.spring.user.security.jwt.TokenInfo;
import account.spring.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.MemberUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
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
    //== 로그아웃 ==//

    //== 권한 확인 ==//
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

    //== 정보 수정 ==//


}
