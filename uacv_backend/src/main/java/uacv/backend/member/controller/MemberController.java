package uacv.backend.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import uacv.backend.member.domain.Member;
import uacv.backend.member.domain.MemberAuthorizationUtil;
import uacv.backend.member.domain.Response;
import uacv.backend.member.dto.*;
import uacv.backend.member.security.jwt.TokenInfo;
import uacv.backend.member.service.MemberService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final View error;

    //== 계정생성 ==//
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/create")
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

        if (passwordEncoder.matches(password, DbPassword)) {
            TokenInfo tokenInfo = memberService.login(username, DbPassword);
            return tokenInfo;
        }

        return memberService.login(username, password);
    }

    //== 회원삭제 ==//
    @DeleteMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        return memberService.deleteMember(id);
    }

    //== 중복확인 ==//
    @GetMapping("/check")
    public String checkUsername (@RequestParam String username) {
        System.out.println(username);

        Member member = null;

        try {
            member = memberService.findUser(username);
            return "사용불가";

        } catch (IllegalStateException e) {
            return "사용가능";
        }

    }

    //== 회원 리스트 출력 ==//
    @GetMapping("/list")
    public List<MemberDto> getMemberList() {
        return memberService.memberList();
    }

    //== id로 회원 찾기 ==//
    @GetMapping("/{id}")
    public MemberDto getMemberById(@PathVariable Long id) {
        return memberService.findUserById(id);
    }

    //== 회원정보수정 ==//
    // 1. 비밀번호 변경
    @PutMapping("/update/password")
    public String updatePassword(@RequestBody UpdatePasswordDto updatePassword) {
        String username = MemberAuthorizationUtil.getLoginUsername();
        memberService.updatePassword(username, updatePassword.getNewPassword());

        return "비밀번호 수정 완료";
    }

    // 2. 권한 변경
    @PutMapping("update/role")
    public String updateRole(@RequestBody UpdateRoleDto updateRole) {
        memberService.updateRole(updateRole.getUsername(), updateRole.getMemberRole());

        return "권한 수정 완료";
    }
}
