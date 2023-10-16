package backend.projects.competitionApp.filter;


import backend.projects.competitionApp.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JWTGeneratorFilter extends OncePerRequestFilter {

    //Jwts.builder genera el HEADER. El PAYLOAD contiene las reclamaciones del token, los claim()
    //La firma se genera en la línea: .signWith(key).compact();
    //key es la clave secreta generada de aplicar el algoritmo HMAC-SHA al String SecurityConstants.JWT_KEY

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Comprobar el valor de authentication una vez logueado o no logueado cual es el valor
        if (authentication != null) {
            //Generacion del JWT
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder().setIssuer("g7s").setSubject("JWT")
                    .claim("username", authentication.getName())
                    .claim("authorities", authentication.getAuthorities())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + 30000000))
                    .signWith(key).compact();
            response.setHeader(SecurityConstants.JWT_HEADER, jwt);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/api/user");
    }
}
