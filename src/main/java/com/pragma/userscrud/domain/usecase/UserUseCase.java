package com.pragma.userscrud.domain.usecase;


import com.pragma.userscrud.domain.api.IUserServicePort;
import com.pragma.userscrud.domain.models.EmployeeRestaurant;
import com.pragma.userscrud.domain.models.Rol;
import com.pragma.userscrud.domain.models.User;
import com.pragma.userscrud.domain.models.UserLogin;
import com.pragma.userscrud.domain.spi.IRolPersistencePort;
import com.pragma.userscrud.domain.spi.IUserPersistencePort;

import com.pragma.userscrud.domain.spi.client.IEmployeeRestaurant;
import  org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IRolPersistencePort rolPersistencePort;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final IEmployeeRestaurant employeeRestaurant;
    private static final String OWNER = "OWNER";
    private static final String EMPLOYEE = "EMPLOYEE";
    private static final String CLIENT = "CUSTOMER";
    private static final String userNotFound = "User not found";


    public UserUseCase(IUserPersistencePort userPersistencePort, IRolPersistencePort rolPersistencePort, PasswordEncoder bCryptPasswordEncoder, IEmployeeRestaurant employeeRestaurant) {
        this.userPersistencePort = userPersistencePort;
        this.rolPersistencePort = rolPersistencePort;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.employeeRestaurant = employeeRestaurant;
    }
    //validations fields and logic
    //validate is email already exists
    private  boolean existsUserByEmail(String email){
        return userPersistencePort.existsUserByEmail(email);
    }
    //validate structure email
    private void isValidEmail(String email){
        if(!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            throw new IllegalArgumentException("Email is not valid");
        }
    }
    //validate phone structure
    private void validatePhone(String phone) {
        if (!phone.matches("\\+?\\d{1,13}")) {
            throw new IllegalArgumentException("Phone must be a maximum of 13 characters and can contain the symbol +");
        }
    }

    // validate document of identity structure
    private void validateCc(String cc) {
        if (!cc.matches("\\d+")) {
            throw new IllegalArgumentException("Document of identity must be numeric");
        }
    }

    //validate fields not null
    private void validateFields(User user){
        if(user.getName() == null || user.getName().replace(" ","").isEmpty() ||
                user.getLastName() == null || user.getLastName().replace(" ","").isEmpty() ||
                user.getCc() == null || user.getCc().replace(" ","").isEmpty() ||
                user.getPhone() == null || user.getPhone().replace(" ","").isEmpty() ||
                user.getBirthDate() == null  ||
                user.getEmail() == null || user.getEmail().replace(" ","").isEmpty() ||
                user.getPassword() == null || user.getPassword().replace(" ","").isEmpty()){
            throw new IllegalArgumentException("All fields are required");
        }
    }

    //validate for create user
    private void validateUser(User user){
        validateFields(user);
        isValidEmail(user.getEmail());
        validatePhone(user.getPhone());
        validateCc(user.getCc());
        if(existsUserByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }
    }
    @Override
    public User registerOwner(User user) {
        final boolean isAdult = user.isAdult();
        if(!isAdult){
            throw new IllegalArgumentException("User must be adult");
        }
        validateUser(user);
        final Rol ownerRol = this.rolPersistencePort.findRolByName(OWNER);
        user.setRol(ownerRol);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userPersistencePort.createUser(user);
    }

    @Override
    public User registerEmployee(User user, String token, Long idRestaurant) {
        if (user.getBirthDate() == null) {
            user.setBirthDate(new Date());
        }
        validateUser(user);
        final Rol employeeRol = this.rolPersistencePort.findRolByName(EMPLOYEE);
        user.setRol(employeeRol);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        final User employee = userPersistencePort.createUser(user);
        EmployeeRestaurant employeeRestaurantModel = new EmployeeRestaurant(employee.getId(), idRestaurant);
        employeeRestaurant.createEmployeeUserRestaurant(employeeRestaurantModel, token);

        return employee;
    }


    @Override
    public User registerClient(User user) {
        if (user.getBirthDate() == null) {
            user.setBirthDate(new Date());
        }
        validateUser(user);
        final Rol employeeRol = this.rolPersistencePort.findRolByName(CLIENT);
        user.setRol(employeeRol);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userPersistencePort.createUser(user);
    }

    @Override
    public User getUserById(Long id) {
        User user = userPersistencePort.getUserById(id);
        if(user == null){
            throw new IllegalArgumentException(userNotFound);
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userPersistencePort.getUserByEmail(email);
        if(user == null){
            throw new IllegalArgumentException(userNotFound);
        }
        return user;
    }

    @Override
    public String login(UserLogin userLogin) {

        User user = userPersistencePort.getUserByEmail(userLogin.getEmail());
        if(user == null){
            throw new IllegalArgumentException(userNotFound);
        }
        if(!bCryptPasswordEncoder.matches(userLogin.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Password is not valid");
        }
        List<String> authorities = new ArrayList<>();
        authorities.add(user.getRol().getName());

        return  userPersistencePort.generateToken(user.getEmail(), authorities, user.getName(), user.getLastName());
    }




}
