package com.pragma.userscrud.domain;

import com.pragma.userscrud.domain.models.EmployeeRestaurant;
import com.pragma.userscrud.domain.models.Rol;
import com.pragma.userscrud.domain.models.User;
import com.pragma.userscrud.domain.models.UserLogin;
import com.pragma.userscrud.domain.spi.IRolPersistencePort;
import com.pragma.userscrud.domain.spi.IUserPersistencePort;
import com.pragma.userscrud.domain.spi.client.IEmployeeRestaurant;
import com.pragma.userscrud.domain.usecase.UserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUsecaseTest {

    @InjectMocks
    private UserUseCase userUsecase;

    @Mock
    private IRolPersistencePort rolPersistencePort;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private IEmployeeRestaurant employeeRestaurant;

    @Test
    public void testRegisterEmployee() {
        Rol employeeRol = new Rol();
        employeeRol.setName("EMPLOYEE");

        User actualUser = new User();
        actualUser.setName("Maria");
        actualUser.setLastName("Gomez");
        actualUser.setCc("987654321");
        actualUser.setPhone("987654321");
        actualUser.setBirthDate(new Date());
        actualUser.setEmail("maria@gmail.com");
        actualUser.setPassword("password");

        User expectedUser = new User();
        expectedUser.setName("Maria");
        expectedUser.setLastName("Gomez");
        expectedUser.setCc("987654321");
        expectedUser.setPhone("987654321");
        expectedUser.setBirthDate(new Date());
        expectedUser.setEmail("maria@gmail.com");
        expectedUser.setPassword("encodedPassword");
        expectedUser.setRol(employeeRol);

        when(rolPersistencePort.findRolByName("EMPLOYEE")).thenReturn(employeeRol);
        when(userPersistencePort.createUser(any(User.class))).thenReturn(expectedUser);
        when(bCryptPasswordEncoder.encode("password")).thenReturn("encodedPassword");

        User resultUser = userUsecase.registerEmployee(actualUser, "token", 1L);

        verify(userPersistencePort).createUser(actualUser);
        verify(employeeRestaurant).createEmployeeUserRestaurant(any(EmployeeRestaurant.class), eq("token"));
        assertEquals(expectedUser.getEmail(), resultUser.getEmail());
    }

    @Test
    public void testRegisterClient() {
        Rol clientRol = new Rol();
        clientRol.setName("CUSTOMER");

        User actualUser = new User();
        actualUser.setName("Carlos");
        actualUser.setLastName("Diaz");
        actualUser.setCc("123123123");
        actualUser.setPhone("123123123");
        actualUser.setBirthDate(new Date());
        actualUser.setEmail("carlos@gmail.com");
        actualUser.setPassword("password");

        User expectedUser = new User();
        expectedUser.setName("Carlos");
        expectedUser.setLastName("Diaz");
        expectedUser.setCc("123123123");
        expectedUser.setPhone("123123123");
        expectedUser.setBirthDate(new Date());
        expectedUser.setEmail("carlos@gmail.com");
        expectedUser.setPassword("encodedPassword");
        expectedUser.setRol(clientRol);

        when(rolPersistencePort.findRolByName("CUSTOMER")).thenReturn(clientRol);
        when(userPersistencePort.createUser(any(User.class))).thenReturn(expectedUser);
        when(bCryptPasswordEncoder.encode("password")).thenReturn("encodedPassword");

        User resultUser = userUsecase.registerClient(actualUser);

        verify(userPersistencePort).createUser(actualUser);
        assertEquals(expectedUser.getEmail(), resultUser.getEmail());
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setName("Laura");
        user.setLastName("Martinez");
        user.setId(1L);

        when(userPersistencePort.getUserById(1L)).thenReturn(user);

        User resultUser = userUsecase.getUserById(1L);

        assertEquals(user.getId(), resultUser.getId());
        assertEquals(user.getName(), resultUser.getName());
    }

    @Test
    public void testGetUserByIdNotFound() {
        when(userPersistencePort.getUserById(2L)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userUsecase.getUserById(2L);
        });

        assertEquals("User not found", exception.getMessage());
    }
    @Test
    public void testLoginSuccess() {
        // Configura los valores correctos para los argumentos
        String email = "test@gmail.com";
        String password = "password";
        List<String> roles = Arrays.asList("OWNER");

        Rol ownerRol = new Rol();
        ownerRol.setName("OWNER");

        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedPassword");  // Suponiendo que esta es la contraseña codificada almacenada
        user.setRol(ownerRol);

        when(userPersistencePort.getUserByEmail(email)).thenReturn(user);

        // Simula la verificación de la contraseña
        when(bCryptPasswordEncoder.matches(password, user.getPassword())).thenReturn(true);

        when(userPersistencePort.generateToken(email, roles, user.getName(), user.getLastName())).thenReturn("mockToken");

        String token = userUsecase.login(new UserLogin(email, password));
        assertEquals("mockToken", token);
    }



    @Test
    public void testLoginInvalidPassword() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("encodedPassword");

        UserLogin userLogin = new UserLogin("test@gmail.com", "wrongPassword");

        when(userPersistencePort.getUserByEmail("test@gmail.com")).thenReturn(user);
        when(bCryptPasswordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userUsecase.login(userLogin);
        });

        assertEquals("Password is not valid", exception.getMessage());
    }

    @Test
    public void testLoginUserNotFound() {
        UserLogin userLogin = new UserLogin("notfound@gmail.com", "password");

        when(userPersistencePort.getUserByEmail("notfound@gmail.com")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userUsecase.login(userLogin);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testRegisterEmployeeUserAlreadyExists() {
        User user = new User();
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("existing@gmail.com");
        user.setPhone("+1234567890");
        user.setCc("123456789");
        user.setPassword("password");
        user.setBirthDate(new Date());

        when(userPersistencePort.existsUserByEmail("existing@gmail.com")).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userUsecase.registerEmployee(user, "token", 1L);
        });

        assertEquals("Email already exists", exception.getMessage());
    }

}
