package account.spring.user.service;

import account.spring.user.domain.Member;
import account.spring.user.domain.MemberRole;
import account.spring.user.dto.SignupDto;
import account.spring.user.repository.UserRepository;
import account.spring.user.security.jwt.JwtTokenProvider;
import account.spring.user.security.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
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
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    //== 회원가입 ==//
    @Transactional
    public Member signUp(SignupDto signupDto) {
        Member member = new Member();
        member.setUsername(signupDto.getUsername());
        member.setPassword(new BCryptPasswordEncoder().encode(signupDto.getPassword1()));
        member.setMemberRole(signupDto.getMemberRole());

        return userRepository.save(member);
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
        return userRepository.findAll();
    }

    //== username으로 해당 member 찾기 ==//
    public Member findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalStateException("User not found");
        });
    }


}
