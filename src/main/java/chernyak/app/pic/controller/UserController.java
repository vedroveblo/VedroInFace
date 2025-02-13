package chernyak.app.pic.controller;

import chernyak.app.pic.model.Role;
import chernyak.app.pic.model.User;
import chernyak.app.pic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Контроллер для управления пользователями
// Обрабатывает HTTP-запросы, связанные с регистрацией и входом
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Эндпоинт для регистрации нового пользователя с ролью
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam Role role) {
        userService.registerUser(username, email, password, role);
        return ResponseEntity.ok("User registered successfully");
    }

    // Эндпоинт для аутентификации пользователя
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @RequestParam String username,
            @RequestParam String password) {
        String token = userService.authenticateUser(username, password);
        return ResponseEntity.ok(token);
    }
}
