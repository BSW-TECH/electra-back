package com.electra.server.controller;

import com.electra.server.config.ws.WebSocketConfig;
import com.electra.server.service.api.SessionApi;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private SessionApi sessionApi;
    @CrossOrigin("http://localhost:8080")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/open")
    public ResponseEntity<String> open(Principal principal) {
        return ResponseEntity.ok(WebSocketConfig.PATH_PREFIX + sessionApi.requestNewSession(principal.getName()).toString());
    }

    @CrossOrigin("http://localhost:8080")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/close")
    public ResponseEntity<String> close(@RequestBody Integer sessionId) {
        sessionApi.closeSession(sessionId);
        return ResponseEntity.ok("Closed");
    }
}
