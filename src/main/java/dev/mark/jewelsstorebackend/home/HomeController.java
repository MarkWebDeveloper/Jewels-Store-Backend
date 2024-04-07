package dev.mark.jewelsstorebackend.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(path = "")
    public String index() {
        
        return "Hello PrintGo! Proyecto pedagógico Factoría F5.";
        
    }
    
}