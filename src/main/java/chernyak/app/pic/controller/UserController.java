package chernyak.app.pic.controller;

import chernyak.app.pic.dto.LoginRequest;
import chernyak.app.pic.dto.UserDto;
import chernyak.app.pic.model.User;
import chernyak.app.pic.repository.UserRepository;
import chernyak.app.pic.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // Регистрация через JSON
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        userService.registerUser(
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRole()
        );
        return ResponseEntity.ok("User registered successfully");
    }

    // Логин через JSON
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest request) {
        String token = userService.authenticateUser(
                request.getUsername(),
                request.getPassword()
        );
        return ResponseEntity.ok(token);
    }
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()") // Разрешено только если токен есть и валиден
    public User getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }
}

