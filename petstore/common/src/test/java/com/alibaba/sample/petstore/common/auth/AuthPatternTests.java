package com.alibaba.sample.petstore.common.auth;

import static org.junit.Assert.*;

import org.junit.Test;

import com.alibaba.sample.petstore.common.auth.impl.AuthPattern;

public class AuthPatternTests {
    private AuthPattern pattern;

    @Test(expected = IllegalArgumentException.class)
    public void create_noname1() {
        new AuthPattern(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_noname2() {
        new AuthPattern(" ");
    }

    @Test
    public void getPatternName() {
        pattern = new AuthPattern("test");
        assertEquals("/**/test", pattern.getPatternName());

        pattern = new AuthPattern("/test");
        assertEquals("/test", pattern.getPatternName());
    }

    @Test
    public void getPattern() {
        // relative path
        pattern = new AuthPattern("test");

        assertMatches(true, "/a/b/test");
        assertMatches(true, "/test");

        assertMatches(false, "/testa");
        assertMatches(false, "/test/");
        assertMatches(false, "test");
        assertMatches(false, "test/");
        assertMatches(false, "/a/b/test/");

        // abs path
        pattern = new AuthPattern("/t/est");

        assertMatches(false, "/a/b/t/est");
        assertMatches(true, "/t/est");

        assertMatches(false, "/t/esta");
        assertMatches(false, "/t/est/");
        assertMatches(false, "t/est");
        assertMatches(false, "t/est/");
        assertMatches(false, "/a/b/t/est/");
    }

    private void assertMatches(boolean matches, String s) {
        assertEquals(s, matches, pattern.getPattern().matcher(s).matches());
    }

    @Test
    public void toString_() {
        assertEquals("AuthPattern[/**/test]", new AuthPattern(" test ").toString());
    }
}
