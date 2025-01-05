package savenow.backend.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import savenow.backend.config.auth.LoginUser;
import savenow.backend.entity.user.Role;
import savenow.backend.entity.user.User;

import java.util.Date;

public class JwtProcess {
    private final Logger log = LoggerFactory.getLogger(getClass());

    // 토큰 생성 로직
    public static String create(LoginUser loginUser) {
        String jwtToken = JWT.create()
                .withSubject("savenow") // jwt 이름
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVO.Expiration_TIME)) // 일주일 후 만료
                .withClaim("id", loginUser.getUser().getId()) // 유저 식별용
                .withClaim("role", loginUser.getUser().getRole()+"") // 유저 권한 확인용
                .sign(Algorithm.HMAC512(JwtVO.SECRET));
        return JwtVO.TOKEN_PREFIX + jwtToken;
    }

    public static LoginUser verify(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtVO.SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();
        User user = User.builder().id(id).role(Role.valueOf(role)).build();
        LoginUser loginUser = new LoginUser(user);
        return loginUser;
    }

    // 토큰 리플래쉬
    public static String refresh(String token) {
        return null;
    }

}
