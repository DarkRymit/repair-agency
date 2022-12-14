package com.epam.finalproject.model.event;


import com.epam.finalproject.model.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
public class OnPasswordResetEvent extends ApplicationEvent {

    private final Locale locale;
    private final User user;

    public OnPasswordResetEvent(final User user, final Locale locale) {
        super(user);
        this.user = user;
        this.locale = locale;
    }
}
