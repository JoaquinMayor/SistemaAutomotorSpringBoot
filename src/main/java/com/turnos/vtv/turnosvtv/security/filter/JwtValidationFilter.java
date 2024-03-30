package com.turnos.vtv.turnosvtv.security.filter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.turnos.vtv.turnosvtv.security.filter.TokenJwtConfig.*;

//Cración del token según el tipo de acceso
public class JwtValidationFilter extends BasicAuthenticationFilter{
    
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException, java.io.IOException {
                
            String header = request.getHeader(HEADER_AUTORIZATION); //Primero recuperamos el header, del token del cliente o postman

            if(header == null || !header.startsWith(PREFIX_TOKEN)){
                chain.doFilter(request, response);
                return ;
            }

            String token = header.replace(PREFIX_TOKEN, ""); //Le quitamos el prefix tooken para dejarlo limpio

            try{
                Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
                String username = claims.getSubject();
                Object authoritiesClaims = claims.get("authorities");

                Collection<? extends GrantedAuthority> authorities =Arrays.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthoritiesJsonCreator.class).readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));
                
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null, authorities);//el password se valida solo cuando creamos el token
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); //Se autentica
                chain.doFilter(request, response);
            }catch(JwtException e){
               Map<String, String> body = new HashMap<>();
               body.put("error", e.getMessage());
               body.put("message", "El token JWT no es valido");

               response.getWriter().write(new ObjectMapper().writeValueAsString(body));
               response.setStatus(401);
               response.setContentType("aplication/json");

            }
           
    } 
}
