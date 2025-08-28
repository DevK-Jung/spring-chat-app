package com.example.springchatapp.app.user.entity;

import com.example.springchatapp.core.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter

@Entity
@Builder
@Table(name = "tn_user")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.OFFLINE;

    public enum UserStatus {
        ONLINE, OFFLINE, AWAY
    }
}
