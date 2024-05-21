package dev.mark.jewelsstorebackend.config.oauth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token { 
      
    private String userId; 
    private String accessToken; 
    private String refreshToken;    
  
} 