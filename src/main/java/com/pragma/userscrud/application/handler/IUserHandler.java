package com.pragma.userscrud.application.handler;

import com.pragma.userscrud.application.dto.request.ClientDtoRequest;
import com.pragma.userscrud.application.dto.request.EmployeeDtoRequest;
import com.pragma.userscrud.application.dto.request.LoginUserDtoRequest;
import com.pragma.userscrud.application.dto.request.OwnerDtoRequest;
import com.pragma.userscrud.application.dto.response.EmployeeDtoResponse;
import com.pragma.userscrud.application.dto.response.LoginUserDtoResponse;
import com.pragma.userscrud.application.dto.response.OwnerDtoResponse;
import com.pragma.userscrud.application.dto.response.UserDtoResponse;


public interface IUserHandler {
    OwnerDtoResponse registerOwner(OwnerDtoRequest ownerDtoRequest);
    EmployeeDtoResponse registerEmployee(EmployeeDtoRequest employeeDtoRequest, String token);
    EmployeeDtoResponse registerClient(ClientDtoRequest clientDtoRequest);
    UserDtoResponse getUserById(Long id);
    UserDtoResponse getUserByEmail(String email);
    LoginUserDtoResponse login(LoginUserDtoRequest loginUserDtoRequest);
}
