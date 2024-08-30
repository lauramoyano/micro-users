package com.pragma.userscrud.application.handler.services;

import com.pragma.userscrud.application.dto.request.ClientDtoRequest;
import com.pragma.userscrud.application.dto.request.EmployeeDtoRequest;
import com.pragma.userscrud.application.dto.request.LoginUserDtoRequest;
import com.pragma.userscrud.application.dto.request.OwnerDtoRequest;
import com.pragma.userscrud.application.dto.response.EmployeeDtoResponse;
import com.pragma.userscrud.application.dto.response.LoginUserDtoResponse;
import com.pragma.userscrud.application.dto.response.OwnerDtoResponse;
import com.pragma.userscrud.application.dto.response.UserDtoResponse;
import com.pragma.userscrud.application.handler.IUserHandler;
import com.pragma.userscrud.application.mapper.request.IUserRequestMapper;
import com.pragma.userscrud.application.mapper.response.IUserResponseMapper;
import com.pragma.userscrud.domain.api.IUserServicePort;
import com.pragma.userscrud.domain.models.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceimpl implements IUserHandler {

        private final IUserRequestMapper userRequestMapper;
        private final IUserResponseMapper userResponseMapper;
        private final IUserServicePort userServicePort;
        private final AuthenticationManager authenticationManager;


        @Override
        public OwnerDtoResponse registerOwner(OwnerDtoRequest ownerDtoRequest) {
            final User user = this.userRequestMapper.mapOwnerDtoRequestToUser(ownerDtoRequest);
            final User userRegister = this.userServicePort.registerOwner(user);
            return this.userResponseMapper.mapUserToOwnerDtoResponse(userRegister);
        }

        @Override
        public UserDtoResponse getUserById(Long id) {
            String rolName = this.userServicePort.getUserById(id).getRol().getName();
            UserDtoResponse user = this.userResponseMapper.mapUserToUserDtoResponse(this.userServicePort.getUserById(id));
            user.setRolName(rolName);
            return user;
        }

        @Override
        public UserDtoResponse getUserByEmail(String email) {
            String rolName = this.userServicePort.getUserByEmail(email).getRol().getName();
            UserDtoResponse user = this.userResponseMapper.mapUserToUserDtoResponse(this.userServicePort.getUserByEmail(email));
            user.setRolName(rolName);
            return  user;
        }

        @Override
        public LoginUserDtoResponse login(LoginUserDtoRequest loginUserDtoRequest) {
            try{
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginUserDtoRequest.getEmail(), loginUserDtoRequest.getPassword()));
            } catch (AuthenticationException e){
                throw new RuntimeException("Invalid email/password");
            }
            String token = this.userServicePort.login(this.userRequestMapper.mapLoginUserDtoRequestToUserLogin(loginUserDtoRequest));
            LoginUserDtoResponse loginUserDtoResponse = new LoginUserDtoResponse();
            loginUserDtoResponse.setToken(token);
            return loginUserDtoResponse;

        }

        @Override
        public EmployeeDtoResponse registerEmployee(EmployeeDtoRequest employeeDtoRequest, String token) {
            final User user = this.userRequestMapper.mapEmployeeDtoRequestToUser(employeeDtoRequest);
            final User userRegister = this.userServicePort.registerEmployee(user, token, employeeDtoRequest.getIdRestaurant());
            return this.userResponseMapper.mapUserToEmployeeDtoResponse(userRegister);
        }

        @Override
        public EmployeeDtoResponse registerClient(ClientDtoRequest clientDtoRequest) {
            final User user = this.userRequestMapper.mapClientDtoRequestToUser(clientDtoRequest);
            final User userRegister = this.userServicePort.registerClient(user);
            return this.userResponseMapper.mapUserToEmployeeDtoResponse(userRegister);
        }


}
