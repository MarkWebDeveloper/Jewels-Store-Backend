package dev.mark.jewelsstorebackend.products;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// Doc: https://docs.spring.io/spring-framework/docs/6.0.2/reference/html/testing.html#spring-mvc-test-framework

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false) // disable security
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ProductService service;

    @Test
    @DisplayName("Should return a list of products")
    void testIndex() throws JsonProcessingException, Exception {

        List<Product> products = new ArrayList<>();

        Product statueDTO = new Product();
        statueDTO.setId(1L);
        statueDTO.setProductName("Statue");

        Product toy = new Product();
        toy.setId(2L);
        toy.setProductName("Toy");

        products.add(statueDTO);
        products.add(toy);

        when(service.getAll()).thenReturn(products);
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/products")
                .accept(MediaType.APPLICATION_JSON)
                .content("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), containsString("Statue"));
        assertThat(response.getContentAsString(), containsString("Toy"));
        assertThat(response.getContentAsString(), equalTo(mapper.writeValueAsString(products)));
    }

    @Test
    @DisplayName("Should return product with id 1")
    void testShowMethode() throws Exception {
        Product statueDTO = new Product();
        statueDTO.setId(1L);
        statueDTO.setProductName("Statue");

        when(service.getById(1L)).thenReturn(statueDTO);
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/products/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), containsString(statueDTO.getProductName()));
    }

    @Test
    @DisplayName("Should return created product")
    void testPostProduct() throws Exception {
        ProductDTO statueDTO = new ProductDTO();
        statueDTO.productName = "Statue";

        Product statue = new Product();
        statue.setId(1L);
        statue.setProductName("Statue");

        String json = mapper.writeValueAsString(statueDTO); 

        when(service.save(statueDTO)).thenReturn(statue);
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/products")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .content(json))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
    }


}