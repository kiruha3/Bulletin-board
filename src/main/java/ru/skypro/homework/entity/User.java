package ru.skypro.homework.entity;

import lombok.Data;
<<<<<<< HEAD
import ru.skypro.homework.dto.Role;
=======
>>>>>>> origin/dev-kirill

import javax.persistence.*;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}
