package com.example.authorbookrest.util;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {

    public String trim(String text) {
        if (text == null) {
            return null;
        }
        return text.trim();
    }

}
