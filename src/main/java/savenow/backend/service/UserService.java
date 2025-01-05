package savenow.backend.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import savenow.backend.entity.user.User;
import savenow.backend.entity.user.UserRepository;
import savenow.backend.dto.user.UserReqDto.JoinReqDto;
import savenow.backend.dto.user.UserResDto.JoinResDto;
import savenow.backend.handler.exception.CustomApiException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final BCryptPasswordEncoder passwordEncoder;


    // 회원 가입 서비스
    @Transactional
    public JoinResDto join(JoinReqDto joinReqDto) {
        // 아이디 중복 검사
        int nd = nameDuplicateCheck(joinReqDto.getUsername());
        if (nd == 1) {
            //이메일 중복
            throw new CustomApiException("동일한 사용자 이름이 존재합니다.");
        }

        //  닉네임 중복 검사
        int ed = emailDuplicateCheck(joinReqDto.getEmail());
        if (ed == 1) {
            // 닉네임 중복
            throw new CustomApiException("이미 존재하는 이메일 입니다.");
        }

        // 패스워드 인코딩 + 회원가입
        User newUser = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        // 기본 카테고리 설정

        // DTO 응답
        return new JoinResDto(newUser);
    }

    // 이메일 중복 검사 // 에러 -> 1 정상 -> 0
    public int emailDuplicateCheck(String email) {
        Optional<User> userOP = userRepository.findByEmail(email);
        if (userOP.isPresent()){
            return 1;
        }
        return 0;
    }

    public int nameDuplicateCheck(String username) {
        Optional<User> userOP = userRepository.findByUsername(username);
        if (userOP.isPresent()) {
            return 1;
        }
        return 0;
    }





}
