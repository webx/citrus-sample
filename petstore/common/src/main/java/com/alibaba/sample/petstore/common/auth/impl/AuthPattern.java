package com.alibaba.sample.petstore.common.auth.impl;

import static com.alibaba.citrus.util.Assert.*;
import static com.alibaba.citrus.util.StringUtil.*;
import static com.alibaba.citrus.util.internal.regex.PathNameWildcardCompiler.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代表一个用来匹配的pattern。
 * 
 * @author Michael Zhou
 */
public class AuthPattern {
    private final String patternName;
    private final Pattern pattern;

    public AuthPattern(String patternName) {
        patternName = assertNotNull(trimToNull(patternName), "patternName");

        // 对于相对路径，自动在前面加上/，变成绝对路径。
        if (!patternName.startsWith("/")) {
            patternName = "/" + patternName;
        }

        this.patternName = patternName;
        this.pattern = compilePathName(patternName, FORCE_MATCH_PREFIX | FORCE_ABSOLUTE_PATH);
    }

    public String getPatternName() {
        return patternName;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Matcher matcher(String s) {
        return pattern.matcher(s);
    }

    @Override
    public int hashCode() {
        return 31 + patternName.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        return patternName.equals(((AuthPattern) other).patternName);
    }

    @Override
    public String toString() {
        return patternName;
    }
}
