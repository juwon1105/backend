package savenow.backend.config.jwt;

/*
 * 리플래시 토큰 구현 x
 */
public interface JwtVO {

    public static final String SECRET = "세이브나우"; // HS256
    public static final int Expiration_TIME = 1000 * 60 * 60 * 24 * 7;// 일주일
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
}
