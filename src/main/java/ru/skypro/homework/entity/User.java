package ru.skypro.homework.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="users")
@Getter
@Setter
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String username;
    private String password;
    private boolean enabled;
    @OneToOne
    private Image image;
}
