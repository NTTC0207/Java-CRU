//package com.okta.developer.crud.Controller;
//
//import com.okta.developer.crud.Service.UsersService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//@RestController
//@RequestMapping("/api/user")
//public class UserController {
//
//    private final UsersService usersService;
//
//
//    public UserController(UsersService usersService) {
//        this.usersService = usersService;
//    }
//
//    @GetMapping("/all")
//    public CompletableFuture<String> getSubID(@AuthenticationPrincipal OAuth2User user) {
//        String sub = user.getAttribute("sub");
//        return CompletableFuture.completedFuture(sub);
//    }
//}
