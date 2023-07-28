package com.okta.developer.crud.Interface;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.concurrent.CompletableFuture;

public interface ProfileInterface {

    String getEmail(@AuthenticationPrincipal OAuth2User user);
    CompletableFuture<ResponseEntity<String>> getProfile(@AuthenticationPrincipal OidcUser oidcUser);

}
