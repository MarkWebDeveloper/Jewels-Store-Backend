package dev.mark.jewelsstorebackend.users;

import java.util.Collection; 
import java.util.Collections; 
  
import org.springframework.security.core.GrantedAuthority; 
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails { 
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id; 
  
    private String username; 
  
    private String password; 

    public User(Long id, String username, String password) { 
        super(); 
        this.id = id; 
        this.username = username; 
        this.password = password; 
    } 

    public User( String userName, String password) { 
        super(); 
          
        this.username = userName; 
        this.password = password; 
    } 

    // Methods required by Spring Security for user details 
    public Collection<? extends GrantedAuthority> getAuthorities() { 
        return Collections.emptyList(); 
    } 
  
    public boolean isAccountNonExpired() { 
        return true; 
    } 
  
    public boolean isAccountNonLocked() { 
        return true; 
    } 
  
    public boolean isCredentialsNonExpired() { 
        return true; 
    } 
  
    public boolean isEnabled() { 
        return true; 
    }
      
} 