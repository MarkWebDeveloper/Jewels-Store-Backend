package dev.mark.jewelsstorebackend.products.facades;

import org.springframework.stereotype.Component;

@Component
public class ProductFacade implements IProductFacade{

    ImageDelete imageDelete;
    ProductDelete productDelete;

    public ProductFacade(ImageDelete imageDelete, ProductDelete productDelete) {
        this.imageDelete = imageDelete;
        this.productDelete = productDelete;
    }

    @Override
    public String delete(String type, Long id) {

        String response = "";

        if (type == "image") response = imageDelete.delete(id);
        if (type == "product") response = productDelete.delete(id);

        return response;
    }
    
}