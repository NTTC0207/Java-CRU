package com.okta.developer.crud.Service;
import com.okta.developer.crud.Repository.MonthRepository;
import com.okta.developer.crud.model.Month;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class MonthService {
    private final MonthRepository repository;
    private final UsersService usersService;

    public MonthService(MonthRepository repository, UsersService usersService) {
        this.repository = repository;
        this.usersService = usersService;
    }


    @Async
    public CompletableFuture<List<Month>> getAll(@AuthenticationPrincipal OAuth2User user) {

        CompletableFuture<String> userIDFuture = usersService.getSubID(user);
        String userID = userIDFuture.join();
;
        List<Month> months = this.repository.findBySub(userID);

        for (Month month : months) {
            month.setFormattedDate();
        }

        return CompletableFuture.completedFuture(months);
    }



}
