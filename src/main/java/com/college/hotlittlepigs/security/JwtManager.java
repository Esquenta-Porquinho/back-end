package com.college.hotlittlepigs.security;

import com.college.hotlittlepigs.user.dto.UserLoginResponseDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
public class JwtManager {

  public UserLoginResponseDTO createToken(String email, List<String> roles) {
    var calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, SecurityConstant.JWT_EXP_DAYS);

    var jwt =
        Jwts.builder()
            .setSubject(email)
            .setExpiration(calendar.getTime())
            .claim(SecurityConstant.JWT_ROLE_KEY, roles)
            .signWith(SignatureAlgorithm.HS512, SecurityConstant.API_KEY.getBytes())
            .compact();

    Long expireIn = calendar.getTimeInMillis();

    return new UserLoginResponseDTO(jwt, expireIn, SecurityConstant.JWT_PROVIDER);
  }

  public Claims parseToken(String jwt) throws JwtException {
    return Jwts.parser()
        .setSigningKey(SecurityConstant.API_KEY.getBytes())
        .parseClaimsJws(jwt)
        .getBody();
  }
}
