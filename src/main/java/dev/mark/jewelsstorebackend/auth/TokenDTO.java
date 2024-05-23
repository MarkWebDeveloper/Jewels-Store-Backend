package dev.mark.jewelsstorebackend.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token { 
      
    private Long userId; 
    private String accessToken; 
    private String refreshToken;    
  
} 