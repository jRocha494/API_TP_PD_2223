package pt.isec.api_tp_pd_2223.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.isec.api_tp_pd_2223.model.User;
import pt.isec.api_tp_pd_2223.service.UserService;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public ResponseEntity<List<User>> getAllUsers(Authentication jwt){
        var authority = jwt.getAuthorities().iterator().next();

        if(!authority.getAuthority().contains("ADMIN")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }

        return ResponseEntity.ok().body(userService.getAllUsers());
    }
}
