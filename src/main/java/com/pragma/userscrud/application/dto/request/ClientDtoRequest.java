package com.pragma.userscrud.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDtoRequest {
    private String name;
    private String lastName;
    private String cc;
    private String phone;
    private String email;
    private String password;

}
