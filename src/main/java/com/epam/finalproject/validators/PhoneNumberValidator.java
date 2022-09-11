package com.epam.finalproject.validators;


import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements
                                  ConstraintValidator<PhoneNumber, String> {

    PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Override
    public boolean isValid(String contactField,
      ConstraintValidatorContext cxt) {
        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(contactField,
                    CountryCodeSource.UNSPECIFIED.name());
            return phoneNumberUtil.isValidNumber(number);
        } catch (NumberParseException e) {
            return false;
        }
    }

}