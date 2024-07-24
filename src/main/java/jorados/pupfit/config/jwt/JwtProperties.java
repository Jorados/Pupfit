package jorados.pupfit.config.jwt;


public interface JwtProperties {
    String SECRET = "조성진"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 864000000; // 10일 (1/1000초)
    String TOKEN_PREFIX = "Bearer ";   //토큰 인증타입 //내서버에서 만든 JWT 토큰이라는 뜻
    String HEADER_STRING = "Authorization";
}