package account.spring.user.service;

import account.spring.user.dto.RegisterDto;
import account.spring.user.domain.Member;
import account.spring.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Member register(RegisterDto registerDto) {
        Member member = new Member();
        member.setUsername(registerDto.getUsername());
        member.setPassword(new BCryptPasswordEncoder().encode(registerDto.getPassword1()));
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
}
