package com.credits.coopCredit.infrastructure.web.dto.auth;

import java.util.Set;
import com.credits.coopCredit.domain.model.Role;

/**
 * DTO para respuesta de registro.
 */
public class RegisterResponse {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
    private String message;

    public RegisterResponse() {
    }

    public RegisterResponse(Long id, String username, String email, String firstName, String lastName, Set<Role> roles,
            String message) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
