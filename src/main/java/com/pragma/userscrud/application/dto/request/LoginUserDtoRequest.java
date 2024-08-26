package com.pragma.userscrud.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDtoRequest {

    private String email;
    private String password;
}
