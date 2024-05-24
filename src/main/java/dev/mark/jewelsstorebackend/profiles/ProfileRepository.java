package dev.mark.jewelsstorebackend.profiles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long>{
    public Optional<Profile> findByEmail(String name);
}
