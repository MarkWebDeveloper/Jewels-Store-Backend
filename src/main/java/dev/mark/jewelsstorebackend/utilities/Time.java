package dev.mark.jewelsstorebackend.utilities;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class Time {

    static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
    static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT);
    static final SimpleDateFormat SIMPLEDATE_FORMAT = new SimpleDateFormat(TIMESTAMP_FORMAT);

    public String checkCurrentTime() {
        String currentTime = LocalDateTime.now().format(DATETIME_FORMATTER);
        return currentTime;
    }
}
