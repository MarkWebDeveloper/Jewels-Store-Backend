package dev.mark.jewelsstorebackend.products;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_country")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column
    private String description;

    @Column(name = "product_image")
    private String productImage;

    @Column
    private int price;
}
