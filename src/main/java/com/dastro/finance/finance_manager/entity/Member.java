package com.dastro.finance.finance_manager.entity;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "user_info")
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String passwd;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private Role role;

    int failLoginCnt;

    @Builder
    public Member(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public Member update(String name) {
        this.name = name;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
