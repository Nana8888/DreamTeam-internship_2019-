package com.app.controllers;

import com.app.configs.JwtTokenProvider;
import com.app.services.AuthorizationService;
import com.app.validators.UserValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import sun.net.www.protocol.http.AuthenticationHeader;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthorizationController {

   private AuthorizationService userService;
   private UserValidator userValidator;
   private JwtTokenProvider tokenProvider;
   private AuthenticationManager authenticationManager;



}
