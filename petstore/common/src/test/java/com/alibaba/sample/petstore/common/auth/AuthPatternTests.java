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
        assertEquals("/test", pattern.getPatternName());

        pattern = new AuthPattern("/test");
        assertEquals("/test", pattern.getPatternName());
    }

    @Test
    public void getPattern() {
        // relative path
        pattern = new AuthPattern("test");

        assertMatches(false, "/a/b/test");
        assertMatches(false, "/a/test/");
        assertMatches(false, "/a/test/b");
        assertMatches(true, "/test");
        assertMatches(true, "/test/");
        assertMatches(true, "/test/b");

        assertMatches(false, "/atest");
        assertMatches(false, "/testb");
        assertMatches(false, "/atestb");
        assertMatches(false, "test");
        assertMatches(false, "test/");

        // abs path
        pattern = new AuthPattern("/t/est");

        assertMatches(false, "/a/b/t/est");
        assertMatches(false, "/a/t/est/");
        assertMatches(false, "/a/t/est/b");
        assertMatches(true, "/t/est");
        assertMatches(true, "/t/est/");
        assertMatches(true, "/t/est/b");

        assertMatches(false, "/at/est");
        assertMatches(false, "/t/estb");
        assertMatches(false, "/at/estb");
        assertMatches(false, "t/est");
        assertMatches(false, "t/est/");
    }

    private void assertMatches(boolean matches, String s) {
        assertEquals(s, matches, pattern.matcher(s).find());
        assertEquals(s, matches, pattern.getPattern().matcher(s).find());
    }

    @Test
    public void equalsAndHashCode() {
        AuthPattern p1 = new AuthPattern("test");
        AuthPattern p2 = new AuthPattern("/test");
        AuthPattern p3 = new AuthPattern("/*/test");
        AuthPattern p4 = new AuthPattern("/");

        assertEquals(p1, p1);
        assertEquals(p1, p2);
        assertFalse(p1.equals(p3));
        assertFalse(p1.equals(null));
        assertFalse(p1.equals("test"));

        assertEquals(p1.hashCode(), p2.hashCode());
        assertFalse(p1.hashCode() == p3.hashCode());
        assertFalse(p4.hashCode() == p3.hashCode());
    }

    @Test
    public void toString_() {
        assertEquals("/test", new AuthPattern(" test ").toString());
    }
}
