package com.pragma.userscrud.infrastructure.restControllerUser;


import com.pragma.userscrud.application.dto.request.ClientDtoRequest;
import com.pragma.userscrud.application.dto.request.EmployeeDtoRequest;
import com.pragma.userscrud.application.dto.request.LoginUserDtoRequest;
import com.pragma.userscrud.application.dto.request.OwnerDtoRequest;
import com.pragma.userscrud.application.dto.response.EmployeeDtoResponse;
import com.pragma.userscrud.application.dto.response.LoginUserDtoResponse;
import com.pragma.userscrud.application.dto.response.OwnerDtoResponse;
import com.pragma.userscrud.application.dto.response.UserDtoResponse;
import com.pragma.userscrud.application.handler.IUserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRestController {
    private final IUserHandler userHandler;



    @PostMapping("/registerOwner")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<OwnerDtoResponse> registerOwner(@RequestBody OwnerDtoRequest ownerDtoRequest) {
        OwnerDtoResponse ownerRegister = this.userHandler.registerOwner(ownerDtoRequest);
        return new ResponseEntity<>(ownerRegister, org.springframework.http.HttpStatus.CREATED);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable Long id) {
        UserDtoResponse user = this.userHandler.getUserById(id);
        return new ResponseEntity<>(user, org.springframework.http.HttpStatus.OK);
    }

    @GetMapping("/getUserByEmail/{email}")
    
    public ResponseEntity<UserDtoResponse> getUserByEmail(@PathVariable String email) {
        UserDtoResponse user = this.userHandler.getUserByEmail(email);
        return new ResponseEntity<>(user, org.springframework.http.HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginUserDtoResponse> login(@RequestBody LoginUserDtoRequest loginUserDtoRequest) {
        return ResponseEntity.ok(userHandler.login(loginUserDtoRequest));
    }

    @PostMapping("/registerEmployee")
    @PreAuthorize(value = "hasRole('OWNER')")
    public  ResponseEntity<EmployeeDtoResponse> registerEmployee(@RequestBody EmployeeDtoRequest employeeDtoRequest,
                                                                 @RequestHeader("Authorization") String token) {
        EmployeeDtoResponse employeeRegister = this.userHandler.registerEmployee(employeeDtoRequest, token);
        return new ResponseEntity<>(employeeRegister, org.springframework.http.HttpStatus.CREATED);
    }

    @PostMapping("/registerClient")
    public  ResponseEntity<EmployeeDtoResponse> registerClient(@RequestBody ClientDtoRequest clientDtoRequest) {
        EmployeeDtoResponse clientRegister = this.userHandler.registerClient(clientDtoRequest);
        return new ResponseEntity<>(clientRegister, org.springframework.http.HttpStatus.CREATED);
    }
}

