package com.turnos.vtv.turnosvtv.security.filter;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//Grstión de accesos a los distintos tipos de controladores
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {
    
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration; 

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager(); 
    }
    @Bean
    PasswordEncoder passwordEncoder(){ 
        return new BCryptPasswordEncoder(); 
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests((authz) -> authz
        .requestMatchers(HttpMethod.GET,"/api/shift").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET, "/api/shift/myObject").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET, "/api/shift/{patent}").hasRole("ADMIN")
        .requestMatchers(HttpMethod.POST,"/api/shift").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET,"/api/users").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET,"/api/users/email/{email}").permitAll()
        .requestMatchers(HttpMethod.GET,"/api/users/lastname/{lastname}").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET,"/api/users/dni/{dni}").hasRole("ADMIN")
        .requestMatchers(HttpMethod.POST,"/api/users").permitAll()
        .requestMatchers(HttpMethod.PUT,"/api/users/dni/{dni}").hasAnyRole("ADMIN","USER")
        .requestMatchers(HttpMethod.GET,"/api/users/changePassword/{dni}/{password}").hasAnyRole("ADMIN","USER")
        .requestMatchers(HttpMethod.GET,"/api/users/changeName/{dni}/{name}").hasAnyRole("ADMIN","USER")
        .requestMatchers(HttpMethod.GET,"/api/users/changeLastname/{dni}/{lastname}").hasAnyRole("ADMIN","USER")
        .requestMatchers(HttpMethod.GET,"/api/users/changeAge/{dni}/{age}").hasAnyRole("ADMIN","USER")
        .requestMatchers(HttpMethod.GET,"/api/users//changeEmail/{dni}/{email}").hasAnyRole("ADMIN","USER")
        .requestMatchers(HttpMethod.PUT,"api/users/add/vehicle/{dni}").hasAnyRole("ADMIN","USER")
        .requestMatchers(HttpMethod.PUT,"/api/users/change/{patent}/{dniFirst}/{dniSecond}").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET,"/api/vehicles").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET,"/api/vehicles/{patent}").hasRole("ADMIN")
        .requestMatchers(HttpMethod.POST,"/api/vehicles").hasAnyRole("ADMIN","USER")
        .requestMatchers(HttpMethod.PUT,"/api/vehicles/{patent}").hasAnyRole("ADMIN","USER")
        .anyRequest().authenticated())
        .addFilter(new JwtAuthenticationFilter(authenticationManager())) 
        .addFilter(new JwtValidationFilter(authenticationManager()))
        .csrf(config ->config.disable())
        .cors(cors-> cors.configurationSource(corsConfigurationSource())) 
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build(); 
    } 
        
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*")); 
        config.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT","PATCH")); 
        config.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); 
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter(){
        @SuppressWarnings("null")
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource())); 

        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return corsBean;
    }
}
