package com.alibaba.sample.petstore.common.auth;

import static org.junit.Assert.*;

import org.junit.Test;

import com.alibaba.sample.petstore.common.auth.impl.AuthGrant;
import com.alibaba.sample.petstore.common.auth.impl.AuthMatch;
import com.alibaba.sample.petstore.common.auth.impl.AuthPattern;

public class AuthMatchTests {
    private AuthMatch match;

    @Test(expected = IllegalArgumentException.class)
    public void create_noType() {
        new AuthMatch(null, new AuthPattern("test"), new AuthGrant[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_noPatterns() {
        new AuthMatch("target", null, new AuthGrant[0]);
    }

    @Test
    public void getPattern() {
        match = new AuthMatch("target", new AuthPattern("test"), new AuthGrant[0]);
        assertEquals("/**/test", match.getPattern().getPatternName());
    }

    @Test
    public void getGrants() {
        match = new AuthMatch("target", new AuthPattern("test"), new AuthGrant[] { new AuthGrant() });
        assertEquals(1, match.getGrants().length);
    }

    @Test
    public void toString_() {
        match = new AuthMatch("target", new AuthPattern("test"), new AuthGrant[] { new AuthGrant() });

        String s = "";

        s += "AuthMatch {\n";
        s += "  target = /**/test\n";
        s += "  grants = [\n";
        s += "             [1/1] Grant {\n";
        s += "                     allow = []\n";
        s += "                     deny  = []\n";
        s += "                   }\n";
        s += "           ]\n";
        s += "}";

        assertEquals(s, match.toString());
    }
}
