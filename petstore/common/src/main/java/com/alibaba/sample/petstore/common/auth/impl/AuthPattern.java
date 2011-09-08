package com.alibaba.sample.petstore.common.auth.impl;

import static com.alibaba.citrus.util.Assert.*;
import static com.alibaba.citrus.util.StringUtil.*;
import static com.alibaba.citrus.util.internal.regex.PathNameWildcardCompiler.*;

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

        // 对于相对路径，自动在前面加上/**，表示从头部开始匹配。
        if (!patternName.startsWith("/")) {
            patternName = "/**/" + patternName;
        }

        this.patternName = patternName;
        this.pattern = compilePathName(patternName, FORCE_MATCH_PREFIX);
    }

    public String getPatternName() {
        return patternName;
    }

    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return "AuthPattern[" + patternName + "]";
    }
}
