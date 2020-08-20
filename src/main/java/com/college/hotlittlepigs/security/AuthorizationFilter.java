package com.college.hotlittlepigs.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.college.hotlittlepigs.exception.Error;
import com.college.hotlittlepigs.exception.JwtException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(jwt == null || !jwt.startsWith(SecurityConstant.JWT_PROVIDER)) throw new JwtException(SecurityConstant.JWT_INVALID_MSG);

        jwt = jwt.replace(SecurityConstant.JWT_PROVIDER, "");

        try {
            Claims claims = new JwtManager().parseToken(jwt);
			String email = claims.getSubject();
			List<String> roles = (List<String>) claims.get(SecurityConstant.JWT_ROLE_KEY);
			
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			roles.forEach(role -> {
				grantedAuthorities.add(new SimpleGrantedAuthority(role));
			});
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, grantedAuthorities);
			
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
        } catch (Exception e) {
            Error error = new Error(HttpStatus.UNAUTHORIZED.value(),e.getMessage(), new Date(), null);
            PrintWriter writer = response.getWriter();

            ObjectMapper mapper = new ObjectMapper();
            String errorString = mapper.writeValueAsString(error);
            writer.write(errorString);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            return;
        }

        filterChain.doFilter(request, response);
    }
    
}