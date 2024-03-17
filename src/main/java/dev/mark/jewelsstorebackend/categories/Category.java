package dev.mark.jewelsstorebackend.categories;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.mark.jewelsstorebackend.images.Image;
import dev.mark.jewelsstorebackend.products.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "categories")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_category")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id_image")
    private Image image;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "categories")
    Set<Product> products;
}