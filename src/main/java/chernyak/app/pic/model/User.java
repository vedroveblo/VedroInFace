package chernyak.app.pic.model;

import jakarta.persistence.*;
import lombok.Data;

public enum Role {
    USER, ADMIN
}

// Класс-сущность, представляющий пользователя в системе
@Entity // Определяет, что этот класс является JPA-сущностью (таблицей в БД)
@Table(name = "users") // Определяет имя таблицы в базе данных
@Data  // Lombok-аннотация, автоматически генерирующая геттеры, сеттеры, equals, hashCode и toString
public class User {
    @Id // Определяет первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоинкрементное значение первичного ключа
    private Long id;

    @Column(nullable = false, unique = true) // Поле username не может быть null и должно быть уникальным
    private String username;

    @Column(nullable = false) // Поле password обязательно для заполнения
    private String password;

    @Column(nullable = false, unique = true) // Поле email обязательно и должно быть уникальным
    private String email;

    @Enumerated(EnumType.STRING) // Храним роль пользователя в виде строки
    @Column(nullable = false)
    private Role role;
}
