package savenow.backend.config.jwt;

import org.junit.jupiter.api.Test;
import savenow.backend.config.auth.LoginUser;
import savenow.backend.entity.user.Role;
import savenow.backend.entity.user.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtProcessTest {

    private String createToken() {
        //given
        User user = User.builder().id(1L).role(Role.USER).build();
        LoginUser loginUser = new LoginUser(user);

        //when
        String jwtToken = JwtProcess.create(loginUser);
        return jwtToken;
    }

    @Test
    public void 토큰생성() throws Exception{
        //given
        User user = User.builder().id(1L).role(Role.USER).build();
        LoginUser loginUser = new LoginUser(user);

        //when
        String jwtToken = createToken();
        System.out.println("테스트 : " + jwtToken);

        //then
        assertTrue(jwtToken.startsWith(JwtVO.TOKEN_PREFIX));
    }

    @Test
    public void 토큰검증() throws Exception{
        //given
        String token = createToken();
        String jwtToken = token.replace(JwtVO.TOKEN_PREFIX, "");

        //when
        LoginUser loginUser = JwtProcess.verify(jwtToken);
        System.out.println("테스트 : " + loginUser.getUser().getId());
        System.out.println("테스트 : " + loginUser.getUser().getRole().name());

        //then
        assertThat(loginUser.getUser().getId()).isEqualTo(1L);
        assertThat(loginUser.getUser().getRole()).isEqualTo(Role.USER);
    }

}