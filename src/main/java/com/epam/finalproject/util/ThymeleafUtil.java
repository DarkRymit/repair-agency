package com.epam.finalproject.util;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Component
public class ThymeleafUtil {

    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    private ThymeleafUtil() {
    }

    public static String replaceOrAddParam(String key, String value) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replaceQueryParam(key, value)
                .toUriString();
    }
    public static String format(ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(dateTimeFormatter);
    }
}
