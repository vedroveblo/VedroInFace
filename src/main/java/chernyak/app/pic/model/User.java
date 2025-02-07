package chernyak.app.pic.model;

import jakarta.persistence.*;
import lombok.Data;

// Класс-сущность, представляющий пользователя
@Entity // Обозначает, что этот класс является JPA-сущностью (таблицей в БД)
@Table(name = "users") // Указывает, что сущность будет храниться в таблице "users"
@Data
public class User {
    @Id // Указывает, что поле является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоинкрементное значение первичного ключа
    private Long id;

    @Column(nullable = false, unique = true) // Поле username не может быть null и должно быть уникальным
    private String username;

    @Column(nullable = false) // Поле password обязательно для заполнения
    private String password;

    @Column(nullable = false, unique = true) // Поле email обязательно и должно быть уникальным
    private String email;
}
