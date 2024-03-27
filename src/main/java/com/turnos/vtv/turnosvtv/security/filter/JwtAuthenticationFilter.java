package com.turnos.vtv.turnosvtv.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turnos.vtv.turnosvtv.entities.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import static com.turnos.vtv.turnosvtv.security.filter.TokenJwtConfig.*;

public class JwtAuthenticationFilter  extends UsernamePasswordAuthenticationFilter{
    
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
            throws AuthenticationException {
            User user = null;
            String email = null;
            String password = null;

            try {
                user = new ObjectMapper().readValue(request.getInputStream(), User.class); 
                email = user.getEmail();
                password = user.getPassword();
            } catch (StreamReadException e) {
                e.printStackTrace();
            } catch (DatabindException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } 
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password); 

            return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, 
            Authentication authResult) throws IOException, ServletException {
                
                org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)authResult.getPrincipal(); 
                String email = user.getUsername();
                Collection<? extends GrantedAuthority> roles = authResult.getAuthorities(); 
                Claims claims = Jwts.claims().add("authorities", new ObjectMapper().writeValueAsString(roles)).build(); 
                

                String token = Jwts.builder().subject(email)
                .claims(claims)
                .issuedAt(new Date()) 
                .signWith(SECRET_KEY).compact();
                response.addHeader(HEADER_AUTORIZATION, PREFIX_TOKEN + token); 

                Map<String, String> body = new HashMap<>();
                body.put("token", token);
                body.put("email",email);
                body.put("message", String.format("Hola! Has iniciado sesión con éxito!"));

                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                response.setContentType("aplication/json");
                response.setStatus(200);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
            AuthenticationException failed) throws IOException, ServletException {
                Map<String, String> body = new HashMap<>();
                body.put("message", "Error en la autenticación o password incorrectos");
                body.put("error", failed.getMessage());

                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                response.setStatus(401);
                response.setContentType("aplication/json");
    }
}
