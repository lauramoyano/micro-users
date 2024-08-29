package com.pragma.userscrud.domain.api;

import com.pragma.userscrud.domain.models.User;
import com.pragma.userscrud.domain.models.UserLogin;


public interface IUserServicePort {
    User registerOwner(User user);
    User registerEmployee(User user, String token, Long idRestaurant);
    User registerClient(User user);
    User getUserById(Long id);
    User getUserByEmail(String email);
    String login(UserLogin userLogin);

}
