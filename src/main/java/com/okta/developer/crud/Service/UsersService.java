package com.okta.developer.crud.Service;

import com.okta.developer.crud.Repository.UsersRepository;
import com.okta.developer.crud.model.Users;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final ProfileService profileRepository;

    public UsersService(UsersRepository usersRepository, ProfileService profileRepository) {
        this.usersRepository = usersRepository;
        this.profileRepository = profileRepository;
    }

    @Async
    public CompletableFuture<Users> saveUser(Users users, @AuthenticationPrincipal OAuth2User user){

        var check = usersRepository.findByEmail(profileRepository.getEmail(user));

//        if(check.isPresent()){
//
//        }else{
            Users users1 = new Users();
            users1.setAuth(profileRepository.getEmail(user));
            usersRepository.save(users1);
//        }
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Long> getById( @AuthenticationPrincipal OAuth2User user){
        Optional<Long> userIdOptional = usersRepository.findIdByEmail(profileRepository.getEmail(user));
        return CompletableFuture.completedFuture(userIdOptional.orElse(null));
    }


}
