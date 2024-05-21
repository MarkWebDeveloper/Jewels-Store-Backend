package dev.mark.jewelsstorebackend.config.oauth;

import lombok.Getter; 
import lombok.Setter; 
  
@Getter
@Setter
public class SignUp { 
      
    private String username; 
    private String password; 
      
    public SignUp() { 
          
    } 
      
      
    public SignUp(String username, String password) { 
        super(); 
        this.username = username; 
        this.password = password; 
    } 
    public String getUsername() { 
        return username; 
    } 
    public void setUsername(String username) { 
        this.username = username; 
    } 
    public String getPassword() { 
        return password; 
    } 
    public void setPassword(String password) { 
        this.password = password; 
    } 
      
      
  
} 