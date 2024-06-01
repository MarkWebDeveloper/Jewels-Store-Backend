package dev.mark.jewelsstorebackend.users.register;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

import dev.mark.jewelsstorebackend.auth.SignUpDTO;
import dev.mark.jewelsstorebackend.auth.TokenGenerator;
import dev.mark.jewelsstorebackend.encrypt.EncoderFacade;
import dev.mark.jewelsstorebackend.profiles.Profile;
import dev.mark.jewelsstorebackend.profiles.ProfileRepository;
import dev.mark.jewelsstorebackend.roles.Role;
import dev.mark.jewelsstorebackend.roles.RoleService;
import dev.mark.jewelsstorebackend.users.User;
import dev.mark.jewelsstorebackend.users.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegisterService {

    UserRepository userRepository;
    RoleService roleService;
    EncoderFacade encoder;
    ProfileRepository profileRepository;
    TokenGenerator tokenGenerator;

    public String createUser(SignUpDTO signupDTO) {

        User newUser = new User(signupDTO.getUsername(), signupDTO.getPassword()); 

        System.out.println(newUser.getId());

        String passwordDecoded = encoder.decode("base64", newUser.getPassword());
        String passwordEncoded = encoder.encode("bcrypt", passwordDecoded);
        
        newUser.setPassword(passwordEncoded);
        assignDefaultRole(newUser);

        userRepository.save(newUser);

        User savedUser = userRepository.getReferenceById(newUser.getId().toString());

        Profile newProfile = Profile.builder()
                .id(savedUser.getId())
                .user(savedUser)
                .email(savedUser.getUsername())
                .firstName("")
                .lastName("")
                .address("")
                .postalCode("")
                .numberPhone("")
                .city("")
                .province("")
                .build();

        profileRepository.save(newProfile);

        String message = "User with the username " + newUser.getUsername() + " is successfully created.";

        return message;

    }

    public void assignDefaultRole(User user) {

        Role defaultRole = roleService.getById(2L);
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);

        user.setRoles(roles);
    }
}