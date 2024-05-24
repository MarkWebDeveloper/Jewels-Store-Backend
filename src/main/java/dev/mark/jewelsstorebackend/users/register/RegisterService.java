package dev.mark.jewelsstorebackend.users.register;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import dev.mark.jewelsstorebackend.auth.SignUpDTO;
import dev.mark.jewelsstorebackend.auth.TokenDTO;
import dev.mark.jewelsstorebackend.auth.TokenGenerator;
import dev.mark.jewelsstorebackend.encrypt.EncoderFacade;
import dev.mark.jewelsstorebackend.profiles.Profile;
import dev.mark.jewelsstorebackend.profiles.ProfileRepository;
import dev.mark.jewelsstorebackend.roles.Role;
import dev.mark.jewelsstorebackend.roles.RoleService;
import dev.mark.jewelsstorebackend.users.User;
import dev.mark.jewelsstorebackend.users.UserRepository;
import dev.mark.jewelsstorebackend.users.security.SecurityUser;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegisterService {

    UserRepository userRepository;
    RoleService roleService;
    EncoderFacade encoder;
    ProfileRepository profileRepository;
    TokenGenerator tokenGenerator;

    public TokenDTO createUser(SignUpDTO signupDTO) {

        User newUser = new User(signupDTO.getUsername(), signupDTO.getPassword()); 

        String passwordDecoded = encoder.decode("base64", newUser.getPassword());
        System.out.println(passwordDecoded);
        String passwordEncoded = encoder.encode("bcrypt", passwordDecoded);
        

        newUser.setPassword(passwordEncoded);
        assignDefaultRole(newUser);
        SecurityUser securityUser = new SecurityUser(newUser);

        Profile newProfile = Profile.builder()
                .user(newUser)
                .email(newUser.getUsername())
                .firstName("")
                .lastName("")
                .address("")
                .postalCode("")
                .numberPhone("")
                .city("")
                .province("")
                .build();

        userRepository.save(newUser);

        profileRepository.save(newProfile);
        
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(newUser, signupDTO.getPassword(), securityUser.getAuthorities());
        
        TokenDTO token = tokenGenerator.createToken(authentication);

        return token;

    }

    public void assignDefaultRole(User user) {

        Role defaultRole = roleService.getById(2L);
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);

        user.setRoles(roles);
    }
}