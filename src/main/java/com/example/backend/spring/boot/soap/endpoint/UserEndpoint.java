package com.example.backend.spring.boot.soap.endpoint;

import com.example.backend.spring.boot.soap.entity.User;
import com.example.backend.spring.boot.soap.service.UserService;
import com.example.backend.spring.boot.soap.wsdl.CreateUserRequest;
import com.example.backend.spring.boot.soap.wsdl.CreateUserResponse;
import com.example.backend.spring.boot.soap.wsdl.ObjectFactory;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class UserEndpoint {

    private static final String NS = "http://example.com/soap/crud";
    private final UserService service;
    private final ObjectFactory factory = new ObjectFactory(); // <-- Import above

    @PayloadRoot(namespace = NS, localPart = "CreateUserRequest")
    @ResponsePayload
    public CreateUserResponse create(@RequestPayload CreateUserRequest req) {
        try {
            User entity = User.builder()
                    .name(req.getName())
                    .email(req.getEmail())
                    .phone(req.getPhone())
                    .build();

            User saved = service.create(entity);

            CreateUserResponse res = factory.createCreateUserResponse();
            res.setUser(toDto(saved));
            return res;
        } catch (ConstraintViolationException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private com.example.backend.spring.boot.soap.wsdl.User toDto(User entity) {
        com.example.backend.spring.boot.soap.wsdl.User dto = factory.createUser();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        return dto;
    }
}
