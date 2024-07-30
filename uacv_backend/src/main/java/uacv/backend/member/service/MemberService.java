package uacv.backend.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import uacv.backend.member.domain.Member;
import uacv.backend.member.dto.SignupDto;
import uacv.backend.member.repository.MemberRepository;
import uacv.backend.member.security.jwt.JwtTokenProvider;
import uacv.backend.member.security.jwt.TokenInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberService.class);
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    //== 회원가입 ==//
    @Transactional
    public Member signUp(SignupDto signupDto) {
        Member member = new Member();
        member.setUsername(signupDto.getUsername());
        member.setPassword(new BCryptPasswordEncoder().encode(signupDto.getPassword1()));
        member.setMemberRole(signupDto.getMemberRole());
        return memberRepository.save(member);
    }


    //== 로그인 ==//
    @Transactional
    public TokenInfo login(String username, String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        return tokenInfo;
    }

    //== 회원 리스트 ==//
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    //== username으로 해당 member 찾기 ==//
    public Member findUser(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalStateException("User not found");
        });
    }

    //== 비밀번호 변경 ==//
    @Transactional
    public void updatePassword(String username, String currentPassword, String newPassword) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalStateException("User not found");
        });

        member.updatePassword(passwordEncoder.encode(newPassword));
    }


}
