package com.pragma.userscrud.domain.models;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
    @NotBlank
   private String name;
    @NotBlank
   private  String lastName;
    @NotBlank
    @Pattern(regexp = "\\d+", message = "Document must be numeric")
   private  String cc;
    @Pattern(regexp = "\\+?\\d{1,13}", message = "Phone must be a maximum of 13 characters and can contain the symbol +")
   private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date birthDate;
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    private Rol rol;

    public User() {
    }

    //Constructor initialization
    public User(Long  id,String name,String lastName ,String cc, String phone, Date birthDate, String email, String password, Rol rol) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.cc = cc;
        this.phone = phone;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public int getAge() {
        LocalDate birthDate = this.birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    public boolean isAdult() {
        return getAge() >= 18;
    }





}