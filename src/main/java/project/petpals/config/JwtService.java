package project.petpals.config;

import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;

import com.auth0.jwt.JWT;
import project.petpals.user.domain.UserService;

@Service
public class JwtService {

    private String secret = "secret";

    @Autowired
    private UserService userService;


    public String extractUsername(String token) {
        return JWT.decode(token).getSubject();
    }

    public String extractClaim(String token, String claim) {
        return JWT.decode(token).getClaim(claim).asString();
    }

    public void validateToken(String token, String userEmail) {
        JWT.require(Algorithm.HMAC256(secret)).build().verify(token);

        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                token,
                userDetails.getAuthorities()
        );

        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
    }


    public String generateToken(UserDetails userDetails) {
        Date date = new Date();
        Date expiration = new Date(date.getTime() + 1000 * 60 * 60 * 10);

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("role", userDetails.getAuthorities().toArray()[0].toString())
                .withIssuedAt(date)
                .withExpiresAt(expiration)
                .sign(algorithm);
    }
}
