package com.todoapp.msvc_user.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    // Enum mapeado como texto en la base
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;


    @Column(nullable = false)
    private Boolean active = true;

}
