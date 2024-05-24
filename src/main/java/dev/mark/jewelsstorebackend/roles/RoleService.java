package dev.mark.jewelsstorebackend.roles;

import org.springframework.stereotype.Service;

import dev.mark.jewelsstorebackend.interfaces.IGenericGetService;
import io.micrometer.common.lang.NonNull;

@Service
public class RoleService implements IGenericGetService<Role>{
    
    RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Role getById(@NonNull Long id) {
        Role role = repository.findById(id).orElseThrow( () -> new RoleNotFoundException("Role Not found") );
        return role;
    }
}