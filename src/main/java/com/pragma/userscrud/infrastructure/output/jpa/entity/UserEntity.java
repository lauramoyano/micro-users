package com.pragma.userscrud.infrastructure.output.jpa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "cc")
    private String cc;
    @Column(name = "phone")
    private String phone;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @ManyToOne 
    @JoinColumn(name = "rol", referencedColumnName = "idRol")
    private RolEntity rol;
}
