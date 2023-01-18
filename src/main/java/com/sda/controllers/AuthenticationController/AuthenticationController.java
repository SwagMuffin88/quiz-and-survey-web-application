package com.sda.controllers.AuthenticationController;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (@Valid @RequestBody RegisterRequest  request){
        System.out.println("salu");
        return ResponseEntity.ok(authenticationService.regiter(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest  request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/register")
    public String helo (){
        System.out.println("hey");
        return "helo yaay";
    }

}
