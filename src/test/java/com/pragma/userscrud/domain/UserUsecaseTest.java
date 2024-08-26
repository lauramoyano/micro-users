package com.pragma.userscrud.domain;

import com.pragma.userscrud.domain.models.Rol;
import com.pragma.userscrud.domain.models.User;
import com.pragma.userscrud.domain.spi.IRolPersistencePort;
import com.pragma.userscrud.domain.spi.IUserPersistencePort;
import com.pragma.userscrud.domain.usecase.UserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    public void testRegisterOwner() {
        Rol ownerRol = new Rol();
        ownerRol.setName("OWNER");

        User expectedUser = new User();
        expectedUser.setName("Juan");
        expectedUser.setLastName("Perez");
        expectedUser.setCc("123456789");
        expectedUser.setPhone("123456789");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date birthDate = dateFormat.parse("1990-01-01");
            expectedUser.setBirthDate(birthDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Birth date is not valid");
        }
        expectedUser.setEmail("jua@gmail.com");
        expectedUser.setPassword("encodedPassword");
        expectedUser.setRol(ownerRol);

        User actualUser = new User();
        actualUser.setName("Juan");
        actualUser.setLastName("Perez");
        actualUser.setCc("123456789");
        actualUser.setPhone("123456789");
        try {
            Date birthDate = dateFormat.parse("1990-01-01");
            actualUser.setBirthDate(birthDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Birth date is not valid");
        }
        actualUser.setEmail("jua@gmail.com");
        actualUser.setPassword("123456");

        when(rolPersistencePort.findRolByName("OWNER")).thenReturn(ownerRol);
        when(userPersistencePort.createUser(actualUser)).thenReturn(expectedUser);
        when(bCryptPasswordEncoder.encode("123456")).thenReturn("encodedPassword");

        // When
        User resultUser = userUsecase.registerOwner(actualUser);

        // Then
        verify(userPersistencePort).createUser(actualUser);
        assertEquals(expectedUser.getName(), resultUser.getName());


    }
}