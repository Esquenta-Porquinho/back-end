package com.college.hotlittlepigs.security;

import com.college.hotlittlepigs.exception.ErrorResponse;
import com.college.hotlittlepigs.exception.response.JwtException;
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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
          HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    var jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (jwt == null || !jwt.startsWith(SecurityConstant.JWT_PROVIDER))
      throw new JwtException(SecurityConstant.JWT_INVALID_MSG);

    jwt = jwt.replace(SecurityConstant.JWT_PROVIDER, "");

    try {
      var claims = new JwtManager().parseToken(jwt);
      var email = claims.getSubject();
      var roles = (List<String>) claims.get(SecurityConstant.JWT_ROLE_KEY);

      List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
      roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role)));

      Authentication authentication =
              new UsernamePasswordAuthenticationToken(email, null, grantedAuthorities);

      SecurityContextHolder.getContext().setAuthentication(authentication);

    } catch (Exception e) {
      var error = new ErrorResponse(e.getMessage());
      var writer = response.getWriter();

      var mapper = new ObjectMapper();
      var errorString = mapper.writeValueAsString(error);
      writer.write(errorString);

      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setStatus(HttpStatus.UNAUTHORIZED.value());

      return;
    }

    filterChain.doFilter(request, response);
  }
}
