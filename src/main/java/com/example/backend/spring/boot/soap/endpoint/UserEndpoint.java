package com.example.backend.spring.boot.soap.endpoint;

import com.example.backend.spring.boot.soap.entity.User;
import com.example.backend.spring.boot.soap.service.UserService;
import com.example.backend.spring.boot.soap.wsdl.*;
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


    @PayloadRoot(namespace = NS, localPart = "GetUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest req) {
        User user = service.get(req.getId());

        GetUserResponse res = factory.createGetUserResponse();
        res.setUser(toDto(user));
        return res;
    }

    // UPDATE
    @PayloadRoot(namespace = NS, localPart = "UpdateUserRequest")
    @ResponsePayload
    public UpdateUserResponse update(@RequestPayload UpdateUserRequest req) {
        User update = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .build();

        User updated = service.update(req.getId(), update);

        UpdateUserResponse res = factory.createUpdateUserResponse();
        res.setUser(toDto(updated));
        return res;
    }

    // DELETE
    @PayloadRoot(namespace = NS, localPart = "DeleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse delete(@RequestPayload DeleteUserRequest req) {

        DeleteUserResponse res = factory.createDeleteUserResponse();

        try{
            service.delete(req.getId());
            res.setSuccess(true);
        }catch (Exception e){
            res.setSuccess(false);
        }

        return res;
    }

    // GET ALL
    @PayloadRoot(namespace = NS, localPart = "GetAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAll() {
        GetAllUsersResponse res = factory.createGetAllUsersResponse();
        service.getAll().forEach(u -> res.getUser().add(toDto(u)));
        return res;
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
