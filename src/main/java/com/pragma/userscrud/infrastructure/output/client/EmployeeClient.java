package com.pragma.userscrud.infrastructure.output.client;

import com.pragma.userscrud.domain.models.EmployeeRestaurant;
import com.pragma.userscrud.domain.spi.client.IEmployeeRestaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.HttpHeaders;

import java.net.SocketException;

@RequiredArgsConstructor
@Service
public class EmployeeClient implements IEmployeeRestaurant {
    private final WebClient webClient;
    

    @Override
    public void createEmployeeUserRestaurant(EmployeeRestaurant employeeRestaurant, String token) {
        // Implementation for createEmployeeUserRestaurant
        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/restaurant/employee/create").build())
                .header(HttpHeaders.AUTHORIZATION, token)
                .bodyValue(employeeRestaurant)
                .exchangeToMono(clientResponse -> {
                    if(clientResponse.statusCode().equals(HttpStatus.CREATED)) {
                        return Mono.empty();
                    } else if(clientResponse.statusCode().equals(HttpStatus.UNAUTHORIZED)) {
                        return Mono.error(new AccessDeniedException("Access denied by invalid token"));
                    } else if(clientResponse.statusCode().equals(HttpStatus.FORBIDDEN)) {
                        return Mono.error(new AccessDeniedException("Access denied by insufficient permissions"));
                    } else if(clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new NullPointerException("You don't own any restaurant"));
                    } else {
                        return Mono.error(new SocketException("Unexpected error"));
                    }
                })
                .block();
    }
}