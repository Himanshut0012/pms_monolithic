package com.josh.security.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserCredentials {
    @Id
    String username;
    @Column
    String password;
    @Column
    String email;
    @Column
    String role;
}
