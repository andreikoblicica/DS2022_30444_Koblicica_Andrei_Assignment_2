package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.security.JwtResponse;
import ro.tuc.ds2020.security.JwtUtils;
import ro.tuc.ds2020.security.LoginRequest;
import ro.tuc.ds2020.security.MessageResponse;
import ro.tuc.ds2020.services.AuthService;
import ro.tuc.ds2020.services.UserDetailsImpl;

import javax.validation.Valid;

import static ro.tuc.ds2020.UrlMapping.*;

@RestController
@RequestMapping(AUTH)
@CrossOrigin
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, AuthService authService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping(LOG_IN)
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(
                JwtResponse.builder()
                        .id(userDetails.getId())
                        .token(jwt)
                        .username(userDetails.getUsername())
                        .role(userDetails.getRole())
                        .build()
        );
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<?> registerUser(@Valid @RequestBody LoginRequest loginRequest) {
        if (authService.existsByUsername(loginRequest.getUsername())) {
            return ResponseEntity
                    .ok(new MessageResponse("Error: Username is already taken!"));
        }

        authService.register(loginRequest);


        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }



}
