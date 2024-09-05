package com.pragma.userscrud;

import com.pragma.userscrud.domain.models.EmployeeRestaurant;
import com.pragma.userscrud.domain.models.Rol;
import com.pragma.userscrud.domain.models.User;
import com.pragma.userscrud.domain.spi.IRolPersistencePort;
import com.pragma.userscrud.domain.spi.IUserPersistencePort;
import com.pragma.userscrud.domain.spi.client.IEmployeeRestaurant;
import com.pragma.userscrud.domain.usecase.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolPersistencePort rolPersistencePort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IEmployeeRestaurant employeeRestaurant;

    @InjectMocks
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testRegisterOwner_ValidUser_ShouldReturnRegisteredUser() {
        User user = new User(1L, "John", "Doe", "123456", "+123456789012", new Date(), "john@example.com", "password", null);
        user.setBirthDate(new Date(2000 - 1900, 1, 1));

        when(userPersistencePort.existsUserByEmail(user.getEmail())).thenReturn(false);
        when(rolPersistencePort.findRolByName("OWNER")).thenReturn(new Rol(1L, "OWNER", "Owner"));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userPersistencePort.createUser(any(User.class))).thenReturn(user);

        User registeredUser = userUseCase.registerOwner(user);

        assertNotNull(registeredUser);
        assertEquals("OWNER", registeredUser.getRol().getName());
        assertEquals("encodedPassword", registeredUser.getPassword());

        verify(userPersistencePort, times(1)).existsUserByEmail(user.getEmail());
        verify(rolPersistencePort, times(1)).findRolByName("OWNER");
        verify(passwordEncoder, times(1)).encode("password"); // Verifica que el argumento es "password"
        verify(userPersistencePort, times(1)).createUser(any(User.class));
    }

    @Test
    void testRegisterEmployee_ValidUser_ShouldReturnRegisteredEmployee() {
        User user = new User(1L, "Jane", "Smith", "654321", "+123456789012", new Date(), "jane@example.com", "password", null); // Cambiado a 12 dígitos
        user.setBirthDate(new Date(2000 - 1900, 1, 1));

        when(userPersistencePort.existsUserByEmail(user.getEmail())).thenReturn(false);
        when(rolPersistencePort.findRolByName("EMPLOYEE")).thenReturn(new Rol(2L, "EMPLOYEE", "Employee"));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userPersistencePort.createUser(any(User.class))).thenReturn(user);

        String token = "mockToken";
        Long idRestaurant = 1L;
        User registeredEmployee = userUseCase.registerEmployee(user, token, idRestaurant);

        assertNotNull(registeredEmployee);
        assertEquals("EMPLOYEE", registeredEmployee.getRol().getName());
        assertEquals("encodedPassword", registeredEmployee.getPassword());

        verify(userPersistencePort, times(1)).existsUserByEmail(user.getEmail());
        verify(rolPersistencePort, times(1)).findRolByName("EMPLOYEE");
        verify(passwordEncoder, times(1)).encode("password");
        verify(userPersistencePort, times(1)).createUser(any(User.class));
        verify(employeeRestaurant, times(1)).createEmployeeUserRestaurant(any(EmployeeRestaurant.class), eq(token));
    }

    @Test
    void testRegisterOwner_UnderageUser_ShouldThrowException() {
        User user = new User(1L, "John", "Doe", "123456", "+1234567890", new Date(), "john@example.com", "password", null);
        user.setBirthDate(new Date(2010 - 1900, 1, 1)); // Asegurando que el usuario es menor de edad

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.registerOwner(user);
        });

        assertEquals("User must be adult", exception.getMessage());
        verify(userPersistencePort, never()).existsUserByEmail(user.getEmail());
        verify(rolPersistencePort, never()).findRolByName(anyString());
    }

    @Test
    void testGetUserById_UserExists_ShouldReturnUser() {
        User user = new User(1L, "John", "Doe", "123456", "+1234567890", new Date(), "john@example.com", "password", null);
        when(userPersistencePort.getUserById(1L)).thenReturn(user);

        User foundUser = userUseCase.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(user, foundUser);
        verify(userPersistencePort, times(1)).getUserById(1L);
    }

    @Test
    void testGetUserById_UserDoesNotExist_ShouldThrowException() {
        when(userPersistencePort.getUserById(1L)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userUseCase.getUserById(1L);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userPersistencePort, times(1)).getUserById(1L);
    }
}
