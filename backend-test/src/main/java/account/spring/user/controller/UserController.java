package account.spring.user.controller;

import account.spring.user.dto.MemberLoginRequestDto;
import account.spring.user.security.jwt.TokenInfo;
import account.spring.user.service.UserService;
import account.spring.user.dto.RegisterDto;
import account.spring.user.domain.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signup")
    public Response<?> register(RegisterDto registerDto) {
        return new Response<>(userService.register(registerDto));
    }

    @PostMapping("/login")
    public TokenInfo login(MemberLoginRequestDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getUsername();
        String password = memberLoginRequestDto.getPassword();

        TokenInfo tokenInfo = userService.login(memberId, password);

        return tokenInfo;
    }

}
