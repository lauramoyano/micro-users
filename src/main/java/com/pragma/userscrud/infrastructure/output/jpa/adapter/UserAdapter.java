package com.pragma.userscrud.infrastructure.output.jpa.adapter;

import com.pragma.userscrud.domain.models.EmployeeRestaurant;
import com.pragma.userscrud.domain.models.User;
import com.pragma.userscrud.domain.spi.IUserPersistencePort;
import com.pragma.userscrud.infrastructure.config.Jwt.JwtTokenProvider;
import com.pragma.userscrud.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.pragma.userscrud.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserAdapter implements IUserPersistencePort {
    private final IUserRepository userRepo;
    private final IUserEntityMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public User createUser(User user) {
       return userMapper.mapUserEntityToUser(userRepo.save(userMapper.mapUserToUserEntity(user)));
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.mapUserEntityToUser(userRepo.findById(id).orElse(null));
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.mapUserEntityToUser(userRepo.findByEmail(email).orElse(null));
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepo.existsByEmail(email);
    }


    @Override
    public String generateToken(String email, List<String> authority, String nameFromUser, String lastNameFromUser) {
        //System.out.println("Generating token for: " + email);
        return jwtTokenProvider.generateToken(email, authority, nameFromUser, lastNameFromUser);
    }


}
