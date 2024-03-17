package dev.mark.jewelsstorebackend.images;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.mark.jewelsstorebackend.categories.Category;
import dev.mark.jewelsstorebackend.products.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "images")
public class Image {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_image")
    private Long id;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "is_main_image")
    private boolean isMainImage;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Category category;
}