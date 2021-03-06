package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.nnk.springboot.config.ValidPassword;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "TINYINT")
    private Integer id;

    @Column(name="username",length = 125)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(name = "password", length = 125)
    @NotBlank(message = "Password is mandatory")
    @ValidPassword
    private String password;

    @Column(name="fullname",length = 125)
    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @Column(name="role",length = 125)
    @NotBlank(message = "Role is mandatory")
    private String role;

    public User() {
    }

    public User(@NotBlank(message = "Username is mandatory") String username,
                @NotBlank(message = "Password is mandatory") String password,
                @NotBlank(message = "FullName is mandatory") String fullname,
                @NotBlank(message = "Role is mandatory") String role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
