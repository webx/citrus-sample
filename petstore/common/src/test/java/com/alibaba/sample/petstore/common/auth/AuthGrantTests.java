package com.alibaba.sample.petstore.common.auth;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.sample.petstore.common.auth.impl.AuthGrant;
import com.alibaba.sample.petstore.common.auth.impl.AuthPattern;

public class AuthGrantTests {
    private AuthGrant grant;

    @Before
    public void init() {
        grant = new AuthGrant();
    }

    @Test
    public void setUser() {
        assertNull(grant.getUser());

        grant.setUser(" user ");
        assertEquals("user", grant.getUser());
    }

    @Test
    public void setRole() {
        assertNull(grant.getRole());

        grant.setRole(" role ");
        assertEquals("role", grant.getRole());
    }

    @Test
    public void setAllow() {
        assertTrue(grant.getAllowedActions().isEmpty());

        grant.setAllow(null);
        assertTrue(grant.getAllowedActions().isEmpty());

        grant.setAllow("aa, bb, cc");
        assertArrayEquals(new String[] { "/aa", "/bb", "/cc" }, toArray(grant.getAllowedActions()));

        grant.setAllow(" bb, *, cc");
        assertArrayEquals(new String[] { "/bb", "/*", "/cc" }, toArray(grant.getAllowedActions()));
    }

    @Test
    public void setDeny() {
        assertTrue(grant.getDeniedActions().isEmpty());

        grant.setDeny(null);
        assertTrue(grant.getDeniedActions().isEmpty());

        grant.setDeny("aa, bb, cc");
        assertArrayEquals(new String[] { "/aa", "/bb", "/cc" }, toArray(grant.getDeniedActions()));

        grant.setDeny(" bb, *, cc");
        assertArrayEquals(new String[] { "/bb", "/*", "/cc" }, toArray(grant.getDeniedActions()));
    }

    private String[] toArray(Set<AuthPattern> patterns) {
        String[] s = new String[patterns.size()];
        int i = 0;

        for (AuthPattern pattern : patterns) {
            s[i++] = pattern.getPatternName();
        }

        return s;
    }

    @Test
    public void isActionAllowed() {
        grant.setAllow("*");

        assertTrue(grant.isActionAllowed("/a/b"));
        assertTrue(grant.isActionAllowed("/a"));
        assertTrue(grant.isActionAllowed("/"));

        grant.setAllow("a, b");

        assertTrue(grant.isActionAllowed("/a/b"));
        assertTrue(grant.isActionAllowed("/b"));
        assertFalse(grant.isActionAllowed("/c"));

        grant.setAllow("a/b*");

        assertTrue(grant.isActionAllowed("/a/bc"));
        assertTrue(grant.isActionAllowed("/a/b"));
        assertTrue(grant.isActionAllowed("/a/b/c"));
        assertFalse(grant.isActionAllowed("/c"));
    }

    @Test
    public void isActionDenied() {
        grant.setDeny("*");

        assertTrue(grant.isActionDenied("/a/b"));
        assertTrue(grant.isActionDenied("/a"));
        assertTrue(grant.isActionDenied("/"));

        grant.setDeny("a, b");

        assertTrue(grant.isActionDenied("/a/b"));
        assertTrue(grant.isActionDenied("/b"));
        assertFalse(grant.isActionDenied("/c"));

        grant.setDeny("a/b*");

        assertTrue(grant.isActionDenied("/a/bc"));
        assertTrue(grant.isActionDenied("/a/b"));
        assertTrue(grant.isActionDenied("/a/b/c"));
        assertFalse(grant.isActionDenied("/c"));
    }

    @Test
    public void toString_() {
        grant.setAllow("a,b,c");
        grant.setDeny("e,f,g");
        grant.setUser("user");
        grant.setRole("role");

        String s = "";
        s += "Grant {\n";
        s += "  user  = user\n";
        s += "  role  = role\n";
        s += "  allow = [\n";
        s += "            [1/3] /a\n";
        s += "            [2/3] /b\n";
        s += "            [3/3] /c\n";
        s += "          ]\n";
        s += "  deny  = [\n";
        s += "            [1/3] /e\n";
        s += "            [2/3] /f\n";
        s += "            [3/3] /g\n";
        s += "          ]\n";
        s += "}";

        assertEquals(s, grant.toString());
    }
}
