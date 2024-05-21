package dev.mark.jewelsstorebackend.config.oauth;

import java.util.Collections; 
  
import org.springframework.core.convert.converter.Converter; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.oauth2.jwt.Jwt; 
import org.springframework.stereotype.Component;

import dev.mark.jewelsstorebackend.users.User; 
  
  
  
@Component
public class JWTtoUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> { 
  
    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) { 
         User user = new User(); 
            user.setId(source.getSubject()); 
            return new UsernamePasswordAuthenticationToken(user, source, Collections.EMPTY_LIST); 
    } 
      
  
} 