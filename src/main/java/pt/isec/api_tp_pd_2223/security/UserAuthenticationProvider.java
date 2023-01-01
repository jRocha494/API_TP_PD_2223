package pt.isec.api_tp_pd_2223.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pt.isec.api_tp_pd_2223.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider
{
    private UserService userService;

    public UserAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if(!userService.loginUser(username, password))
            throw new AuthenticationCredentialsNotFoundException("Invalid Credentials");

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (username.equals("admin") && password.equals("admin"))
        {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }else
            authorities.add(new SimpleGrantedAuthority("USER"));

        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }
    @Override
    public boolean supports(Class<?> authentication)
    {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
