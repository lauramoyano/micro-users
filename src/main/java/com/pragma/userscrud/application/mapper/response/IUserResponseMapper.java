package com.pragma.userscrud.application.mapper.response;

import com.pragma.userscrud.application.dto.response.EmployeeDtoResponse;
import com.pragma.userscrud.application.dto.response.OwnerDtoResponse;
import com.pragma.userscrud.application.dto.response.UserDtoResponse;
import com.pragma.userscrud.domain.models.User;
import org.mapstruct.Mapper;

import  org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserResponseMapper {

    UserDtoResponse mapUserToUserDtoResponse(User user);
    OwnerDtoResponse mapUserToOwnerDtoResponse(User user);
    EmployeeDtoResponse mapUserToEmployeeDtoResponse(User user);


}
