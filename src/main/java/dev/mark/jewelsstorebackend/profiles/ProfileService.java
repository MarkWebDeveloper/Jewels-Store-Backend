package dev.mark.jewelsstorebackend.profiles;

import java.util.Set;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dev.mark.jewelsstorebackend.interfaces.IGenericGetService;
import dev.mark.jewelsstorebackend.interfaces.IGenericUpdateService;
import dev.mark.jewelsstorebackend.products.Product;
import dev.mark.jewelsstorebackend.products.ProductNotFoundException;
import dev.mark.jewelsstorebackend.products.ProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfileService implements IGenericUpdateService<ProfileDTO, Profile>, IGenericGetService<Profile> {

    ProfileRepository repository;
    ProductRepository productRepository;

    public Profile getById(@NonNull Long id)throws Exception{
        Profile profile = repository.findById(id).orElseThrow(() -> new ProfileNotFoundException("Profile not found"));

        return profile;
    }

    public Profile getByEmail(@NonNull String email)throws Exception{
        Profile profile = repository.findByEmail(email).orElseThrow(() -> new ProfileNotFoundException("Profile not found"));
        return profile;
    }

    @Override
    public Profile update(ProfileDTO profileDTO, Long id) {
       Profile profile = repository.findById(id).orElseThrow(()-> new ProfileNotFoundException("Profile Not Found"));

       profile.setFirstName(profileDTO.getFirstName());
       profile.setLastName(profileDTO.getLastName());
       profile.setAddress(profileDTO.getAddress());
       profile.setNumberPhone(profileDTO.getNumberPhone());
       profile.setPostalCode(profileDTO.getPostalCode());
       profile.setCity(profileDTO.getCity());
       profile.setProvince(profileDTO.getProvince());

       return repository.save(profile);
    }

    public String updateFavorites(Long productId) throws Exception {
        
        SecurityContext contextHolder = SecurityContextHolder.getContext();
        Authentication auth = contextHolder.getAuthentication();
        
        Profile updatingProfile = repository.findByEmail(auth.getName()).orElseThrow(() -> new ProfileNotFoundException("Profile not found"));

        Product newProduct = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Set<Product> favoriteProducts = updatingProfile.getFavorites();

        String message = "";

        if (favoriteProducts.contains(newProduct)) {
            favoriteProducts.remove(newProduct);
            message = "Product is removed from favorites";
        } else {
            favoriteProducts.add(newProduct);
            message = "Product is added to favorites";
        }

        updatingProfile.setFavorites(favoriteProducts);

        repository.save(updatingProfile);
        
        return message;
    }
    
}