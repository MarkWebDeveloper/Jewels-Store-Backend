package dev.mark.jewelsstorebackend.users;

import java.util.Collection; 
import java.util.Collections; 
  
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority; 
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails { 
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private String id; 
  
    @NonNull
    private String username; 
  
    @NonNull
    private String password; 

    public User(String id, String username, String password) { 
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