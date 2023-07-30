package com.okta.developer.crud.Controller;


import com.okta.developer.crud.Service.MonthService;
import com.okta.developer.crud.model.Month;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/month")
public class MonthController {


    private final MonthService monthService;

    public MonthController(MonthService monthService) {
        this.monthService = monthService;
    }

    @GetMapping
    public CompletableFuture<List<Month>> getall(@AuthenticationPrincipal OAuth2User user){
        return monthService.getAll(user);
    }

}
