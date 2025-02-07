package chernyak.app.pic.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;

// Класс для работы с JWT-токенами
@Component
public class JwtUtil {
    private static final String SECRET_KEY = "your_secret_key"; // Секретный ключ для подписи JWT
    private static final long EXPIRATION_TIME = 86400000; // 1 день (в миллисекундах)

    // Метод для генерации JWT-токена
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Метод для извлечения имени пользователя из токена
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Метод проверки валидности токена
    public boolean isTokenValid(String token) {
        return getClaims(token).getExpiration().after(new Date());
    }

    // Извлекает Claims (полезную нагрузку) из JWT
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
