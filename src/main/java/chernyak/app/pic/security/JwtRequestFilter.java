package chernyak.app.pic.security;

import chernyak.app.pic.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// Фильтр для проверки JWT-токенов в каждом HTTP-запросе
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Извлекаем заголовок Authorization из запроса
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // Проверяем, содержит ли заголовок корректный токен (начинается с "Bearer ")
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Убираем "Bearer "
            try {
                username = jwtUtil.extractUsername(jwt); // Извлекаем username из токена
            } catch (ExpiredJwtException e) {
                System.out.println("Токен истек: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ошибка валидации токена: " + e.getMessage());
            }
        }

        // Если username получен и пользователь еще не аутентифицирован
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Проверяем валидность токена
            if (jwtUtil.isTokenValid(jwt)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response); // Передаем управление следующему фильтру
    }
    }
