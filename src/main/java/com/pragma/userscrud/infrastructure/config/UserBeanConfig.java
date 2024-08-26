package com.pragma.userscrud.infrastructure.config;

import com.pragma.userscrud.domain.api.IUserServicePort;
import com.pragma.userscrud.domain.spi.IRolPersistencePort;
import com.pragma.userscrud.domain.spi.IUserPersistencePort;
import com.pragma.userscrud.domain.usecase.UserUseCase;
import com.pragma.userscrud.infrastructure.config.Jwt.JwtTokenProvider;
import com.pragma.userscrud.infrastructure.output.jpa.adapter.RolAdapter;
import com.pragma.userscrud.infrastructure.output.jpa.adapter.UserAdapter;
import com.pragma.userscrud.infrastructure.output.jpa.mapper.IRolEntityMapper;
import com.pragma.userscrud.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.pragma.userscrud.infrastructure.output.jpa.repository.IRolRespository;
import com.pragma.userscrud.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class UserBeanConfig {

    private final IUserRepository userRepository;
    private final IRolRespository rolRepository;
    private final IUserEntityMapper userMapper;
    private final IRolEntityMapper rolMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserAdapter(userRepository, userMapper, jwtTokenProvider);
    }

    @Bean
    public IRolPersistencePort rolPersistencePort() {
        return new RolAdapter(rolRepository, rolMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), rolPersistencePort(), passwordEncoder());
    }


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
