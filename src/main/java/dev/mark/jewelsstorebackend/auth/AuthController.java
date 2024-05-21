package dev.mark.jewelsstorebackend.auth;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;

@RestController
@NoArgsConstructor
@RequestMapping(path = "")
public class AuthController {
    
    @GetMapping("/authorized")
    public Map<String, String> authorized(@RequestParam String code) {
        return Collections.singletonMap("code", code);
    }
}
