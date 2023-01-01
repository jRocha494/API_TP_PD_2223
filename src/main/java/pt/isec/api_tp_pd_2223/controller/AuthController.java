package pt.isec.api_tp_pd_2223.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isec.api_tp_pd_2223.security.TokenService;
import pt.isec.api_tp_pd_2223.service.UserService;

@RestController
public class AuthController
{
    private final TokenService tokenService;
    private UserService userService;

    public AuthController(TokenService tokenService, UserService userService)
    {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(Authentication authentication)
    {
        return ResponseEntity.ok(tokenService.generateToken(authentication));
    }
}
