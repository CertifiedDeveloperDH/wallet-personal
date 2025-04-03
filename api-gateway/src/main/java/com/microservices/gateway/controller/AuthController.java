package com.microservices.gateway.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String keycloakUrl = "http://localhost:9100/realms/mi-realm/protocol/openid-connect/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", "mi-cliente");
        params.add("grant_type", "password");
        params.add("username", request.getUsername());
        params.add("password", request.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(keycloakUrl, entity, Map.class);

        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        String keycloakUrl = "http://localhost:9100/realms/mi-realm/protocol/openid-connect/logout";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", "mi-cliente");
        params.add("refresh_token", token);  // Keycloak usa refresh_token para logout

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        restTemplate.postForEntity(keycloakUrl, entity, String.class);

        return ResponseEntity.ok("Logout exitoso");
    }
}
