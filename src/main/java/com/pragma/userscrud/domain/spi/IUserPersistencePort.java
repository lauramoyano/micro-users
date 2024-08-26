package com.pragma.userscrud.domain.spi;
import com.pragma.userscrud.domain.models.User;

import java.util.List;


public interface IUserPersistencePort {
    User createUser(User user);
    User getUserById(Long id);
    User getUserByEmail(String email);
    boolean existsUserByEmail(String email);
    String generateToken(String email, List<String> authority, String nameFromUser, String lastNameFromUser);
}
