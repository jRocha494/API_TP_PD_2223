package pt.isec.api_tp_pd_2223.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import pt.isec.api_tp_pd_2223.model.User;
import pt.isec.api_tp_pd_2223.repository.UserRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService
{
    private final JwtEncoder encoder;
    private UserRepository userRepository;

    public TokenService(JwtEncoder encoder, UserRepository userRepository)
    {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    public User loginUser(Integer id){
        var curUser = userRepository.findById(id);
        curUser.setIsAuthenticated(1);
        return userRepository.save(curUser);
    }

    public String generateToken(Authentication authentication)
    {
        var user = userRepository.findByUsernameAndPassword(authentication.getName(), authentication.getCredentials().toString());
        if(user == null)
            return null;

        if(user.getIsAuthenticated() != 0)
            return null;

        loginUser(user.getId());

        Instant now = Instant.now();

        String scope = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.HOURS))
            .subject(authentication.getName())
            .claim("scope", scope)
            .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
