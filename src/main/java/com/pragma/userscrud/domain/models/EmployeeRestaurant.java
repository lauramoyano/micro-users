package com.pragma.userscrud.domain.models;

public class EmployeeRestaurant {
    private Long idEmployee;
    private Long idRestaurant;

    public EmployeeRestaurant() {
    }

    public EmployeeRestaurant(Long idEmployee, Long idRestaurant) {
        this.idEmployee = idEmployee;
        this.idRestaurant = idRestaurant;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }
    
}
