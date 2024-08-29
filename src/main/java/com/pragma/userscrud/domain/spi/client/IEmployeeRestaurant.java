package com.pragma.userscrud.domain.spi.client;

import com.pragma.userscrud.domain.models.EmployeeRestaurant;

public interface IEmployeeRestaurant {

    void createEmployeeUserRestaurant(EmployeeRestaurant employeeRestaurant, String token);

}
