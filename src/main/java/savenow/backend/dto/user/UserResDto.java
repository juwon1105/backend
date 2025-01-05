package savenow.backend.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import savenow.backend.entity.user.User;

public class UserResDto {
    @Getter @Setter
    public static class LoginResDto{
        public Long id; // ??
        public String username;

        public LoginResDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
        }
    }


    @Getter
    @Setter
    @ToString
    public static class JoinResDto{
        private String email;
        private String name;


        public JoinResDto(User user) {
            this.email = user.getEmail();
            this.name = user.getUsername();
        }
    }
}
