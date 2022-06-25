package com.epam.finalproject.util;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class ThymeleafUtil {

    public static String replaceOrAddParam(String key,String value) {

        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replaceQueryParam(key, value)
                .toUriString();
    }
}
