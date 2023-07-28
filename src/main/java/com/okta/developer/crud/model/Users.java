package com.okta.developer.crud.model;

import com.okta.developer.crud.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastname;
    private String firstname;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String image_name;
    private String image_url;
    private Date created_at;
    @Column(name = "auth")
    private String auth;

}