package com.chat.app.config;

import com.chat.app.service.impl.UserDetailsServiceIMPL;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JWTAuthencationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthencationFilter.class);

    private final UserDetailsServiceIMPL userDetailsServiceIMPL;

    private final JwtService jwtService;

    public JWTAuthencationFilter(UserDetailsServiceIMPL userDetailsServiceIMPL, JwtService jwtService) {
        this.userDetailsServiceIMPL = userDetailsServiceIMPL;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String authHeader=request.getHeader("Authorization");
            if(authHeader==null || !authHeader.startsWith("Bearer ")){
                filterChain.doFilter(request,response);
                return;
            }
            String jwt = authHeader.substring(7);
            String username = jwtService.extractUsername(jwt);
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = userDetailsServiceIMPL.loadUserByUsername(username);

                if(userDetails!=null && jwtService.isTokenValidated(jwt)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request,response);

        }catch (AccessDeniedException e) {
            LOGGER.error("You don't have access to authorise the resource");
            throw new AccessDeniedException("You don't have Authorization to Access : ACCESS DENIED");
        }catch(RuntimeException e){
                throw new RuntimeException("Access Denied");
            }
        }

    }
