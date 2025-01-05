package savenow.backend.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import savenow.backend.entity.user.Gender;
import savenow.backend.entity.user.Role;
import savenow.backend.entity.user.User;


public class UserReqDto {

    @Getter @Setter
    public static class NameCheckDto {
        private String username;
    }

    @Getter @Setter
    public static class EmailCheckDto {
        private String email;
    }

    @Getter @Setter
    public static class LoginReqDto {
        private String email;
        private String password;
    }


    @Getter @Setter
    public static class JoinReqDto {
        private String username;
        private String email;
        private String password;
        private String birth;
        private Gender gender;

        public User toEntity(BCryptPasswordEncoder passwordEncoder) {
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .birth(birth)
                    .gender(gender)
                    .role(Role.USER)
                    .build();
        }

    }
}
