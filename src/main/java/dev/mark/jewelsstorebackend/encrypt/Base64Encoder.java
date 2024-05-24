package dev.mark.jewelsstorebackend.encrypt;

import java.util.Base64;

public class Base64Encoder implements IEncoder{

    @Override
    public String encode(String data) {
        String dataEncoded = Base64.getEncoder().encodeToString(data.getBytes());
        return dataEncoded;
    }

    public String decode(String data) {
        byte[] decodeBytes = Base64.getDecoder().decode(data);
        String dataDecoded = new String(decodeBytes);
        return dataDecoded;
    }

}