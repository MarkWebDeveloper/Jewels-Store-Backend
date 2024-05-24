package dev.mark.jewelsstorebackend.encrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptEncoder implements IEncoder {

    BCryptPasswordEncoder encoder;

    public BCryptEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public String encode(String data) {
        String dataEncode = encoder.encode(data);
        return dataEncode;
    }
}
