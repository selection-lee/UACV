package uacv.backend.member.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uacv.backend.member.domain.Member;
import uacv.backend.member.domain.MemberRole;
import uacv.backend.member.dto.MemberDto;
import uacv.backend.member.dto.SignupDto;
import uacv.backend.member.repository.MemberRepository;
import uacv.backend.member.security.jwt.JwtTokenProvider;
import uacv.backend.member.security.jwt.TokenInfo;

import java.util.List;
import java.util.stream.Collectors;

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

    //== username 중복 체크 ==//
    
    //== 로그인 ==//
    @Transactional
    public TokenInfo login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        tokenInfo.setMemberRole(memberRepository.findByUsername(username).get().getMemberRole());
        return tokenInfo;
    }

    //== 회원 삭제 ==//
    @Transactional
    public String deleteMember(Long id) {
        Member member = memberRepository.findById(id).orElse(null);

        memberRepository.delete(member);

        return "회원 삭제 완료";
    }

    //== 회원 리스트 ==//
    public List<MemberDto> memberList() {
        return memberRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private MemberDto convertToDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setUsername(member.getUsername());
        memberDto.setMemberRole(member.getMemberRole());
        memberDto.setCreatedDate(member.getCreatedDate());
        memberDto.setLastModifiedDate(member.getLastModifiedDate());
        return memberDto;
    }

    //== id로 해당 member 찾기 ==//
    public MemberDto findUserById(Long id) {
        return memberRepository.findById(id).stream().map(this::convertToDto).findFirst().orElse(null);
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
        Member member = validatePassword(username, currentPassword);

        member.updatePassword(passwordEncoder.encode(newPassword));

    }

    //== 해당 유저 확인 ==//
    public Member validatePassword(String username, String currentPassword) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }

        return member;
    }

    //== 권한 변경 ==//
    @Transactional
    public void updateRole(String username, MemberRole memberRole) {

        Member member = memberRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalStateException("User not found");
        });
        member.updateRole(memberRole);
    }
}
