package dev.mark.jewelsstorebackend.users;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity; 
import org.springframework.security.access.prepost.PreAuthorize; 
import org.springframework.security.core.annotation.AuthenticationPrincipal; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController; 
  
    @RestController
    @RequestMapping("${api-endpoint}") 
    public class UserController { 
        @Autowired
        UserRepository userRepository; 
  
        @GetMapping("/user/getById/{id}")
        @PreAuthorize("#user.id == #id") 
        public ResponseEntity<UserDTO> user(@AuthenticationPrincipal User user, @PathVariable String id) { 
            return ResponseEntity.ok(UserDTO.from(userRepository.findById(id).orElseThrow())); 
        }
    } 