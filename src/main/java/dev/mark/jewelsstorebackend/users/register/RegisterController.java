package dev.mark.jewelsstorebackend.users.register;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.beans.factory.annotation.Qualifier; 
import org.springframework.http.ResponseEntity; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; 
import org.springframework.security.core.Authentication; 
import org.springframework.security.oauth2.jwt.Jwt; 
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken; 
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider; 
import org.springframework.security.provisioning.UserDetailsManager; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;

import dev.mark.jewelsstorebackend.auth.SignUpDTO;
import dev.mark.jewelsstorebackend.auth.TokenDTO;
import dev.mark.jewelsstorebackend.auth.TokenGenerator;
import dev.mark.jewelsstorebackend.users.User;

import java.util.Collections; 

@RestController
@RequestMapping(path = "${api-endpoint}/users/register") 
public class RegisterController { 

    @Autowired
    TokenGenerator tokenGenerator; 

    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider; 
    
    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider") 
    JwtAuthenticationProvider refreshTokenAuthProvider; 
  
    @PostMapping("/register") 
    public ResponseEntity<TokenDTO> register(@RequestBody SignUpDTO signupDTO) { 
        User user = new User(signupDTO.getUsername(), signupDTO.getPassword()); 
        userDetailsManager.createUser(user);
  
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, signupDTO.getPassword(), Collections.EMPTY_LIST);
  
        return ResponseEntity.ok(tokenGenerator.createToken(authentication)); 
    }
}