package dev.mark.jewelsstorebackend.users;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

public class UserTest {

    User user = new User();

    @Test
    void testProductHas6Attributes() {
        Field[] fields = user.getClass().getDeclaredFields();
        assertThat(fields.length, is(5));
    }
}
