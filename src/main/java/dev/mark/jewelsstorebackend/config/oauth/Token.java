package dev.mark.jewelsstorebackend.config.oauth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token { 
      
    private Long userId; 
    private String accessToken; 
    private String refreshToken;    
  
} 