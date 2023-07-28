package com.okta.developer.crud.Controller;


import com.okta.developer.crud.Repository.UsersRepository;
import com.okta.developer.crud.model.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UsersRepository usersRepository;

    public UserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @GetMapping("/all")
    public CompletableFuture<List<Users>> getAll(){
        return CompletableFuture.completedFuture(this.usersRepository.findAll());
    }

}
