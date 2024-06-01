package dev.mark.jewelsstorebackend.users.register;

import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;

import dev.mark.jewelsstorebackend.auth.SignUpDTO;
import dev.mark.jewelsstorebackend.auth.TokenDTO;
import dev.mark.jewelsstorebackend.auth.TokenGenerator;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path = "${api-endpoint}") 
public class RegisterController { 

    RegisterService service;

    TokenGenerator tokenGenerator; 
  
    @PostMapping("/all/register") 
    public ResponseEntity<TokenDTO> register(@RequestBody SignUpDTO signupDTO) { 

        TokenDTO token = service.createUser(signupDTO);
  
        return ResponseEntity.ok(token); 
    }
}