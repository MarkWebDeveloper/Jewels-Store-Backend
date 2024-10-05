package dev.mark.jewelsstorebackend.products;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import dev.mark.jewelsstorebackend.images.Image;
import dev.mark.jewelsstorebackend.messages.Message;
import dev.mark.jewelsstorebackend.users.UserRepository;

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

    @MockBean
    UserRepository userRepository;

    Product statue;
    Product toy;

    {
        statue = new Product();
        statue.setId(1L);
        statue.setProductName("Statue");

        toy = new Product();
        toy.setId(2L);
        toy.setProductName("Toy");
    }

    @Test
    void testShouldGetAllAndReturnOk() throws JsonProcessingException, Exception {

        List<Product> products = new ArrayList<>();
        products.add(statue);
        products.add(toy);

        when(service.getAll(0, 4)).thenReturn(products);
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/all/products?size=4&page=0")
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
    void testShouldGetByIdAndReturnOk() throws Exception {
        when(service.getById(1L)).thenReturn(statue);
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/all/products/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), containsString(statue.getProductName()));
    }

    @Test
    void testShouldSaveProductAndReturnCreated() throws Exception {
        ProductDTO statueDTO = new ProductDTO();
        statue.setProductName("Statue");

        String json = mapper.writeValueAsString(statueDTO); 

        when(service.save(statueDTO)).thenReturn(statue);
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/admin/products")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .content(json))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
    }

    @Test
    void testShouldUpgradeProductAndReturnOk() throws Exception {
        ProductDTO statueDTO = new ProductDTO();
        statue.setProductName("Updated Statue");

        Product newStatue = new Product();
        newStatue.setId(1L);
        newStatue.setProductName("Updated Statue");

        String json = mapper.writeValueAsString(statue); 

        when(service.update(1L, statueDTO)).thenReturn(newStatue);
        MockHttpServletResponse response = mockMvc.perform(put("/api/v1/admin/products/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .content(json))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }

    @Test
    void testShouldDeleteProductAndReturnOkAndMessage() throws Exception {

        Image image = new Image();
        image.setImageName("statue-image.jpg");
        Set<Image> images = new HashSet<>();
        images.add(image);

        statue.setImages(images);

        Message expectedMessage = new Message();
        expectedMessage.createMessage("Product Statue is deleted successfully. Images with names [statue-image.jpg] are deleted successfully");

        when(service.delete(1L)).thenReturn(expectedMessage);
        MockHttpServletResponse response = mockMvc.perform(delete("/api/v1/admin/products/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }
    


}