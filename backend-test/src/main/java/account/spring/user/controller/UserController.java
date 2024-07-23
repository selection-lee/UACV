package account.spring.user.controller;

import account.spring.user.service.UserService;
import account.spring.user.dto.RegisterDto;
import account.spring.user.domain.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signup")
    public Response<?> register(RegisterDto registerDto) {
        return new Response<>(userService.register(registerDto));
    }


}
