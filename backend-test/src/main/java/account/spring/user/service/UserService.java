package account.spring.user.service;

import account.spring.user.dto.RegisterDto;
import account.spring.user.domain.Member;
import account.spring.user.repository.UserRepository;
import account.spring.user.security.jwt.JwtTokenProvider;
import account.spring.user.security.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public Member register(RegisterDto registerDto) {
        Member member = new Member();
        member.setUsername(registerDto.getUsername());
//        member.setPassword(new BCryptPasswordEncoder().encode(registerDto.getPassword1()));
        member.setPassword(registerDto.getPassword1());
        return userRepository.save(member);

    }

    public List<Member> findAll() {
        return userRepository.findAll();
    }

    public Member findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            return new IllegalStateException("User not found");
        });
    }

    @Transactional
    public TokenInfo login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }
}
