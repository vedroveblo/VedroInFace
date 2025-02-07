package chernyak.app.pic.controller;

import chernyak.app.pic.model.User;
import chernyak.app.pic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Контроллер для управления пользователями
@RestController // Данный класс является REST-контроллером Spring
@RequestMapping("/api/users") // Базовый URL для всех методов этого контроллера
public class UserController {
    private final UserService userService;

    @Autowired // Внедрение зависимости сервиса в контроллер
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Эндпоинт регистрации пользователя
    @PostMapping("/register") // Обрабатывает HTTP POST-запрос на /api/users/register
    public ResponseEntity<User> registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        User registeredUser = userService.registerUser(username, email, password);
        return ResponseEntity.ok(registeredUser); // Возвращает зарегистрированного пользователя
    }
}
