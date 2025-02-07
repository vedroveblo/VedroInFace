package chernyak.app.pic.repository;

import chernyak.app.pic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Интерфейс репозитория для работы с пользователями
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // Поиск пользователя по username
    Optional<User> findByEmail(String email); // Поиск пользователя по email
}
