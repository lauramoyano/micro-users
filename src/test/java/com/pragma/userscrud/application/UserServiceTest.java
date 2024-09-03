package com.pragma.userscrud.application;

import com.pragma.userscrud.application.dto.request.EmployeeDtoRequest;
import com.pragma.userscrud.application.dto.request.OwnerDtoRequest;
import com.pragma.userscrud.application.dto.response.EmployeeDtoResponse;
import com.pragma.userscrud.application.dto.response.OwnerDtoResponse;
import com.pragma.userscrud.application.dto.response.UserDtoResponse;
import com.pragma.userscrud.application.handler.services.UserServiceimpl;
import com.pragma.userscrud.application.mapper.request.IUserRequestMapper;
import com.pragma.userscrud.application.mapper.response.IUserResponseMapper;
import com.pragma.userscrud.domain.api.IUserServicePort;
import com.pragma.userscrud.domain.models.Rol;
import com.pragma.userscrud.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceimpl userServiceimpl;

    @Mock
    private IUserRequestMapper userRequestMapper;

    @Mock
    private IUserResponseMapper userResponseMapper;

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private AuthenticationManager authenticationManager;

    private User user;
    private UserDtoResponse userDtoResponse;



    @Test
    void testRegisterOwner() {
        OwnerDtoRequest ownerDtoRequest = new OwnerDtoRequest();
        ownerDtoRequest.setEmail("owner@gmail.com");

        when(userRequestMapper.mapOwnerDtoRequestToUser(ownerDtoRequest)).thenReturn(user);
        when(userServicePort.registerOwner(user)).thenReturn(user);
        when(userResponseMapper.mapUserToOwnerDtoResponse(user)).thenReturn(new OwnerDtoResponse());

        OwnerDtoResponse result = userServiceimpl.registerOwner(ownerDtoRequest);

        assertNotNull(result);
        verify(userServicePort, times(1)).registerOwner(user);
        verify(userRequestMapper, times(1)).mapOwnerDtoRequestToUser(ownerDtoRequest);
        verify(userResponseMapper, times(1)).mapUserToOwnerDtoResponse(user);
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@gmail.com");

        // Crear y asignar un objeto Rol al User para evitar NullPointerException
        Rol rol = new Rol();
        rol.setName("CISTOMER");
        user.setRol(rol); // Asignar el rol al usuario

        userDtoResponse = new UserDtoResponse();
        userDtoResponse.setEmail("test@gmail.com");
        userDtoResponse.setRolName(rol.getName()); // Asignar el nombre del rol al DTO de respuesta
    }

    @Test
    void testGetUserById() {
        Long id = 1L;

        when(userServicePort.getUserById(id)).thenReturn(user);
        when(userResponseMapper.mapUserToUserDtoResponse(user)).thenReturn(userDtoResponse);

        UserDtoResponse result = userServiceimpl.getUserById(id);

        assertEquals(userDtoResponse.getEmail(), result.getEmail());
        assertEquals(userDtoResponse.getRolName(), result.getRolName());
        verify(userServicePort, times(2)).getUserById(id); // Called twice in the method
        verify(userResponseMapper, times(1)).mapUserToUserDtoResponse(user);
    }

    @Test
    void testGetUserByEmail() {
        String email = "test@gmail.com";

        when(userServicePort.getUserByEmail(email)).thenReturn(user);
        when(userResponseMapper.mapUserToUserDtoResponse(user)).thenReturn(userDtoResponse);

        UserDtoResponse result = userServiceimpl.getUserByEmail(email);

        assertEquals(userDtoResponse.getEmail(), result.getEmail());
        assertEquals(userDtoResponse.getRolName(), result.getRolName());
        verify(userServicePort, times(2)).getUserByEmail(email); // Called twice in the method
        verify(userResponseMapper, times(1)).mapUserToUserDtoResponse(user);
    }






    @Test
    void testRegisterEmployee() {
        EmployeeDtoRequest employeeDtoRequest = new EmployeeDtoRequest();
        employeeDtoRequest.setEmail("employee@gmail.com");
        employeeDtoRequest.setIdRestaurant(1L);

        when(userRequestMapper.mapEmployeeDtoRequestToUser(employeeDtoRequest)).thenReturn(user);
        when(userServicePort.registerEmployee(user, "token", 1L)).thenReturn(user);
        when(userResponseMapper.mapUserToEmployeeDtoResponse(user)).thenReturn(new EmployeeDtoResponse());

        EmployeeDtoResponse result = userServiceimpl.registerEmployee(employeeDtoRequest, "token");

        assertNotNull(result);
        verify(userServicePort, times(1)).registerEmployee(user, "token", 1L);
        verify(userRequestMapper, times(1)).mapEmployeeDtoRequestToUser(employeeDtoRequest);
        verify(userResponseMapper, times(1)).mapUserToEmployeeDtoResponse(user);
    }



    }


