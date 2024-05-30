package dev.mark.jewelsstorebackend.auth;

import org.springframework.core.convert.converter.Converter; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.oauth2.jwt.Jwt; 
import org.springframework.stereotype.Component;

import dev.mark.jewelsstorebackend.users.User;
import dev.mark.jewelsstorebackend.users.UserRepository;
import dev.mark.jewelsstorebackend.users.security.SecurityUser; 
  
  
  
@Component
public class JWTtoUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> { 

    UserRepository userRepository;
  
    public JWTtoUserConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) { 

        User user = userRepository.findById(source.getSubject()).orElseThrow();

         SecurityUser securityUser = new SecurityUser(user); 
            user.setId(Long.parseLong(source.getSubject())); 
            return new UsernamePasswordAuthenticationToken(securityUser, securityUser.getPassword(), securityUser.getAuthorities()); 
    } 
      
  
} 