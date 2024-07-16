package dev.mark.jewelsstorebackend.auth;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import dev.mark.jewelsstorebackend.users.security.JpaUserDetailsService;
import dev.mark.jewelsstorebackend.users.security.SecurityUser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource(properties = {
    "ACCESS_TOKEN_PRIVATE_KEY_PATH=src/main/java/dev/mark/jewelsstorebackend/auth/keys/access-token-private.key",
    "ACCESS_TOKEN_PUBLIC_KEY_PATH=src/main/java/dev/mark/jewelsstorebackend/auth/keys/access-token-public.key",
	"REFRESH_TOKEN_PRIVATE_KEY_PATH=src/main/java/dev/mark/jewelsstorebackend/auth/keys/refresh-token-private.key",
	"REFRESH_TOKEN_PUBLIC_KEY_PATH=src/main/java/dev/mark/jewelsstorebackend/auth/keys/refresh-token-public.key",
    "STRIPE_PUBLIC_KEY=pk_test_123123",
    "STRIPE_SECRET_KEY=sk_test_123123",
	"JWT-ISSUER=http://localhost:8080",
	"JWT-AUDIENCE=JJ"
})
@AutoConfigureMockMvc
public class JWTAuthTest {

	@Autowired
	JwtEncoder jwtEncoder;

	@Autowired
	private MockMvc mvc;

	@Test
	void shouldNotAllowTokensWithAnInvalidAudience() throws Exception {
		String token = mint((claims) -> claims
				.audience(List.of("https://wrong")));
		this.mvc.perform(get("/user/getById/2").header("Authorization", "Bearer " + token))
				.andExpect(status().isUnauthorized())
				.andExpect(header().string("WWW-Authenticate", containsString("aud claim is not valid")));
	}

	@Test
	void shouldNotAllowTokensThatAreExpired() throws Exception {
		String token = mint((claims) -> claims
				.issuedAt(Instant.now().minusSeconds(3600))
				.expiresAt(Instant.now().minusSeconds(3599))
		);
		this.mvc.perform(get("/user/getById/2").header("Authorization", "Bearer " + token))
				.andExpect(status().isUnauthorized())
				.andExpect(header().string("WWW-Authenticate", containsString("Jwt expired")));
	}

	@Test
	void shouldShowAllTokenValidationErrors() throws Exception {
		String expired = mint((claims) -> claims
				.audience(List.of("https://wrong"))
				.issuedAt(Instant.now().minusSeconds(3600))
				.expiresAt(Instant.now().minusSeconds(3599))
		);
		this.mvc.perform(get("/cashcards").header("Authorization", "Bearer " + expired))
				.andExpect(status().isUnauthorized())
				.andExpect(header().exists("WWW-Authenticate"))
				.andExpect(jsonPath("$.errors..description").value(
						containsInAnyOrder(containsString("Jwt expired"), containsString("aud claim is not valid"))));
	}

	private String mint() {
		return mint(consumer -> {});
	}

	private String mint(Consumer<JwtClaimsSet.Builder> consumer) {
		JwtClaimsSet.Builder builder = JwtClaimsSet.builder()
				.issuer("http://localhost:8080")
				.issuedAt(Instant.now())
				.expiresAt(Instant.now().plus(5, ChronoUnit.MINUTES))
				.subject(Long.toString(2))
				.audience(Arrays.asList("JuliaJewelsApp"));
		consumer.accept(builder);
		JwtEncoderParameters parameters = JwtEncoderParameters.from(builder.build());
		return this.jwtEncoder.encode(parameters).getTokenValue();
	}

}
