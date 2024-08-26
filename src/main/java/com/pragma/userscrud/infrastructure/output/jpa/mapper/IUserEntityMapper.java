package com.pragma.userscrud.infrastructure.output.jpa.mapper;

import com.pragma.userscrud.domain.models.User;
import com.pragma.userscrud.infrastructure.output.jpa.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserEntityMapper {
    UserEntity mapUserToUserEntity(User user);
    User mapUserEntityToUser(UserEntity userEntity);

}
