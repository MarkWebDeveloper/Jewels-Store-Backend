package dev.mark.jewelsstorebackend.products;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.mark.jewelsstorebackend.cart.CartItem;
import dev.mark.jewelsstorebackend.categories.Category;
import dev.mark.jewelsstorebackend.images.Image;
import dev.mark.jewelsstorebackend.profiles.Profile;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {
    
    public Product(Long id, String productName, String productDescription, Set<Image> images, Long price,
            Set<Category> categories) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.images = images;
        this.price = price;
        this.categories = categories;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Image> images;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems;

    @Column
    private Long price;

    @Column
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "categories_products", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    Set<Category> categories;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "favorites")
    Set<Profile> profiles;
}