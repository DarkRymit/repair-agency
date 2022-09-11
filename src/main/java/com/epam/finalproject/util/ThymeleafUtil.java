package com.epam.finalproject.util;

import com.epam.finalproject.dto.RoleDTO;
import com.epam.finalproject.dto.UserDTO;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
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
        return zonedDateTime.format(dateTimeFormatter.withLocale(LocaleContextHolder.getLocale()));
    }

    public static String formatNumber(Number target, Integer fractionDigits) {
        if (target == null) {
            return null;
        } else {
            DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance(LocaleContextHolder.getLocale());
            format.setMinimumFractionDigits(0);
            format.setMaximumFractionDigits(fractionDigits);
            format.setDecimalFormatSymbols(new DecimalFormatSymbols(LocaleContextHolder.getLocale()));
            return format.format(target);
        }
    }

    public static boolean hasRole(UserDTO user, String role) {
        return user.getRoles()
                .stream()
                .map(RoleDTO::getName)
                .filter(n -> n.equals(role))
                .findFirst()
                .orElse(null) != null;
    }
}
