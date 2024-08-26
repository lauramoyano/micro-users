package com.pragma.userscrud.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDtoRequest {
    private String name;
    private String lastName;
    private String cc;
    private String phone;
    private Date birthDate;
    private String email;
    private String password;

}
