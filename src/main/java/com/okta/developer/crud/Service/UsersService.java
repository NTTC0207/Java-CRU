package com.okta.developer.crud.Service;

import com.okta.developer.crud.Interface.ProfileInterface;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.CompletableFuture;

@Service
public class UsersService implements ProfileInterface {


    @Async
    public CompletableFuture<String> getSubID(@AuthenticationPrincipal OAuth2User user) {
        String sub = user.getAttribute("sub");
        return CompletableFuture.completedFuture(sub);
    }


}
