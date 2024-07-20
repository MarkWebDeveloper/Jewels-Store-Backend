package dev.mark.jewelsstorebackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "ACCESS_TOKEN_PRIVATE_KEY_PATH=src/main/java/dev/mark/jewelsstorebackend/auth/keys/access-token-private.key",
    "ACCESS_TOKEN_PUBLIC_KEY_PATH=src/main/java/dev/mark/jewelsstorebackend/auth/keys/access-token-public.key",
	"REFRESH_TOKEN_PRIVATE_KEY_PATH=src/main/java/dev/mark/jewelsstorebackend/auth/keys/refresh-token-private.key",
	"REFRESH_TOKEN_PUBLIC_KEY_PATH=src/main/java/dev/mark/jewelsstorebackend/auth/keys/refresh-token-public.key",
    "STRIPE_PUBLIC_KEY=pk_test_123123",
    "STRIPE_SECRET_KEY=sk_test_123123",
	"JWT-ISSUER=http://localhost:8080",
    "JWT-AUDIENCE=JuliaJewelsApp"
})
class JewelsStoreBackendApplicationTests {

	@Test
	void contextLoads() {
	}

	@Bean
	PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
	}

}
