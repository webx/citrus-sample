package com.alibaba.sample.petstore.common.auth;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.sample.petstore.common.auth.impl.AuthGrant;

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
        assertTrue(grant.getAllowedActionSet().isEmpty());

        grant.setAllow(null);
        assertTrue(grant.getAllowedActionSet().isEmpty());

        grant.setAllow("aa, bb, cc");
        assertArrayEquals(new Object[] { "aa", "bb", "cc" }, grant.getAllowedActionSet().toArray());

        grant.setAllow("aa, bb, *, cc");
        assertArrayEquals(new Object[] { "*" }, grant.getAllowedActionSet().toArray());
    }

    @Test
    public void setDeny() {
        assertTrue(grant.getDeniedActionSet().isEmpty());

        grant.setDeny(null);
        assertTrue(grant.getDeniedActionSet().isEmpty());

        grant.setDeny("aa, bb, cc");
        assertArrayEquals(new Object[] { "aa", "bb", "cc" }, grant.getDeniedActionSet().toArray());

        grant.setDeny("aa, bb, *, cc");
        assertArrayEquals(new Object[] { "*" }, grant.getDeniedActionSet().toArray());
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
        s += "            [1/3] a\n";
        s += "            [2/3] b\n";
        s += "            [3/3] c\n";
        s += "          ]\n";
        s += "  deny  = [\n";
        s += "            [1/3] e\n";
        s += "            [2/3] f\n";
        s += "            [3/3] g\n";
        s += "          ]\n";
        s += "}";

        assertEquals(s, grant.toString());
    }
}
