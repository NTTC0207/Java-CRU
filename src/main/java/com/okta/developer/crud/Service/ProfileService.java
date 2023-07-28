package com.okta.developer.crud.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.okta.developer.crud.Interface.ProfileInterface;

import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class ProfileService implements ProfileInterface {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

//    @Override
//    @Async
//    public String getEmailg(@AuthenticationPrincipal OAuth2User user) {
//        String profileJson = claimsToJson(oidcUser.getClaims());
//
//        try {
//            JSONObject profileObject = new JSONObject(Integer.parseInt(profileJson));
//            String email = profileObject.getAsString("email");
//            return email;
//        } catch (Exception e) {
//            // Handle any potential JSON parsing errors
//            return "Somthing wrong";
//        }
//
//    }
    @Override
    @Async
    public String getEmail(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            return "";
        } else {
            String email = user.getAttribute("email");
            return email != null ? email : "";
        }
    }

    @Async
    public CompletableFuture<ResponseEntity<String>> getProfile(@AuthenticationPrincipal OidcUser oidcUser) {
        try {
            String profileJson = claimsToJson(oidcUser.getClaims());
            return CompletableFuture.completedFuture(ResponseEntity.ok(profileJson));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Error processing profile"));
        }
    }

    private String claimsToJson(Map<String, Object> claims) {
        try {
            return objectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(claims);
        } catch (JsonProcessingException jpe) {
            log.error("Error parsing claims to JSON", jpe);
        }
        return "Error parsing claims to JSON.";
    }


    @Bean
    public ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper().registerModule(module);
    }

}
