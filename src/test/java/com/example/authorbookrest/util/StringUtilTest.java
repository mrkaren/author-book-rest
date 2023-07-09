package com.example.authorbookrest.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    private StringUtil stringUtil = new StringUtil();

    @Test
    void trimWithNull() {
        String trim = stringUtil.trim(null);
        assertNull(trim);
    }

    @Test
    void trimWithSpace() {
        String testText = " asdf  ";
        String trim = stringUtil.trim(testText);
        assertEquals("asdf", trim);
    }

    @Test
    void trimWithoutSpace() {
        String testText = "asdf";
        String trim = stringUtil.trim(testText);
        assertEquals("asdf", trim);
    }
}
