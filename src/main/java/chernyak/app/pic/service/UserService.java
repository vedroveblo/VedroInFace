package chernyak.app.pic.service;

import chernyak.app.pic.model.Role;
import chernyak.app.pic.model.User;
import chernyak.app.pic.repository.UserRepository;
import chernyak.app.pic.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

// Сервисный слой для работы с пользователями
// Отвечает за логику регистрации и аутентификации
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // ⚠️ интерфейс, не BCrypt напрямую
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // ✅ получаем из Spring
        this.jwtUtil = jwtUtil;
    }

    // Метод для регистрации нового пользователя с указанием роли
    public User registerUser(String username, String email, String password, Role role) {
        if (userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Username or Email already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Хэшируем пароль
        user.setRole(role); // Устанавливаем роль пользователя
        return userRepository.save(user);
    }

    // Метод для аутентификации пользователя и генерации JWT-токена
    public String authenticateUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            System.out.println("⛔ User not found: " + username);
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();

        System.out.println("✅ User found: " + user.getUsername());
        System.out.println("🔐 Input password: " + password);
        System.out.println("🧠 Stored hash: " + user.getPassword());

        boolean result = passwordEncoder.matches(password, user.getPassword());
        System.out.println("🔍 Password match: " + result);

        if (result) {
            String token = jwtUtil.generateToken(username);
            System.out.println("✅ JWT token generated");
            return token;
        }

        throw new RuntimeException("Invalid credentials");
    }



}


