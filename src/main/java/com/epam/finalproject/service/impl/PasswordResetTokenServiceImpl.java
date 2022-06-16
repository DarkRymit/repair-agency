package com.epam.finalproject.service.impl;

import com.epam.finalproject.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    @Value("${token.reset.password.expiration}")
    private Integer EXPIRATION;


    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

}
