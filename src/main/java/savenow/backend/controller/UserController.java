package savenow.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import savenow.backend.dto.ResponseDto;
import savenow.backend.dto.user.UserResDto.JoinResDto;
import savenow.backend.service.UserService;


import static savenow.backend.dto.user.UserReqDto.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController // 데이터 리턴 서버
public class   UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody  JoinReqDto joinReqDto) {

        JoinResDto joinResDto = userService.join(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", joinResDto), HttpStatus.CREATED);

    }

    @GetMapping("/join/emailCheck")
    public ResponseEntity<?> emailCheck(@RequestParam  EmailCheckDto emailCheckDto) {
        int ed = userService.emailDuplicateCheck(emailCheckDto.getEmail());
        if(ed == 1) {
            return new ResponseEntity<>(new ResponseDto<>(-1,"이메일 중복 검사 실패",null),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "이메일 중복 검사 성공", null), HttpStatus.OK);
    }

    @GetMapping("/join/usernameCheck")
    public ResponseEntity<?> usernameCheck(@RequestParam NameCheckDto nameCheckDto) {
        int nd = userService.nameDuplicateCheck(nameCheckDto.getUsername());
        if (nd == 1) {
            return new ResponseEntity<>(new ResponseDto<>(-1, "닉네임 중복 검사 실패", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "닉네임 중복 검사 성공", null), HttpStatus.OK);
    }
}
