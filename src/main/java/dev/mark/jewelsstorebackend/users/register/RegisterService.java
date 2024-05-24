package dev.mark.jewelsstorebackend.users.register;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

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

    public String save(User newUser) {

        String passwordDecoded = encoder.decode("base64", newUser.getPassword());
        System.out.println(passwordDecoded);
        String passwordEncoded = encoder.encode("bcrypt", passwordDecoded);
        

        newUser.setPassword(passwordEncoded);
        assignDefaultRole(newUser);

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

        return "user stored successfully :" + newUser.getUsername();

    }

    public void assignDefaultRole(User user) {

        Role defaultRole = roleService.getById(2L);
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);

        user.setRoles(roles);
    }
}