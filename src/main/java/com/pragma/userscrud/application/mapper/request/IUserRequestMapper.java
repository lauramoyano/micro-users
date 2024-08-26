package com.pragma.userscrud.application.mapper.request;

import com.pragma.userscrud.application.dto.request.ClientDtoRequest;
import com.pragma.userscrud.application.dto.request.EmployeeDtoRequest;
import com.pragma.userscrud.application.dto.request.LoginUserDtoRequest;
import com.pragma.userscrud.application.dto.request.OwnerDtoRequest;
import com.pragma.userscrud.domain.models.User;
import com.pragma.userscrud.domain.models.UserLogin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface IUserRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rol", ignore = true)
    User mapOwnerDtoRequestToUser(OwnerDtoRequest ownerDtoRequest);

    UserLogin mapLoginUserDtoRequestToUserLogin(LoginUserDtoRequest loginUserDtoRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "birthDate", ignore = true)
    @Mapping(target = "rol", ignore = true)
    User mapEmployeeDtoRequestToUser(EmployeeDtoRequest employeeDtoRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "birthDate", ignore = true)
    @Mapping(target = "rol", ignore = true)
    User mapClientDtoRequestToUser(ClientDtoRequest createClientDto);



}
