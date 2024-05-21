package dev.mark.jewelsstorebackend.users;

import lombok.Builder; 
import lombok.Data; 
  
@Builder
@Data
public class UserDTO { 
    private String id; 
    private String username; 
  
    public static UserDTO from(User user) { 
        return UserDTO.builder()  // Corrected the reference to the builder 
                .id(user.getId()) 
                .username(user.getUsername()) 
                .build(); 
    } 
} 