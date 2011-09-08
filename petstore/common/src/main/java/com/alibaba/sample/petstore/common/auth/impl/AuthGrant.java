package com.alibaba.sample.petstore.common.auth.impl;

import static com.alibaba.citrus.util.BasicConstant.*;
import static com.alibaba.citrus.util.CollectionUtil.*;
import static com.alibaba.citrus.util.ObjectUtil.*;
import static com.alibaba.citrus.util.StringUtil.*;

import java.util.Set;

import com.alibaba.citrus.util.internal.ToStringBuilder;
import com.alibaba.citrus.util.internal.ToStringBuilder.MapBuilder;

/**
 * 代表一个授权，可以对role和user进行授权。
 * 
 * @author Michael Zhou
 */
public class AuthGrant {
    /** MATCH_EVERYTHING代表用户和role时，不包含匿名用户 */
    public final static String MATCH_EVERYTHING = "*";

    /** 特列用户名：匿名用户 */
    public final static String ANONYMOUS_USER = "anonymous";

    private String user;
    private String role;
    private Set<String> allowedNames = createLinkedHashSet();
    private Set<String> deniedNames = createLinkedHashSet();

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = trimToNull(user);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = trimToNull(role);
    }

    public Set<String> getAllowedNames() {
        return allowedNames;
    }

    public void setAllow(String allow) {
        setNames(allowedNames, allow);
    }

    public Set<String> getDeniedNames() {
        return deniedNames;
    }

    public void setDeny(String deny) {
        setNames(deniedNames, deny);
    }

    private void setNames(Set<String> nameSet, String names) {
        for (String name : defaultIfNull(split(names, ", "), EMPTY_STRING_ARRAY)) {
            nameSet.add(name);
        }

        if (nameSet.size() > 1 && nameSet.contains(MATCH_EVERYTHING)) {
            nameSet.clear();
            nameSet.add(MATCH_EVERYTHING);
        }
    }

    @Override
    public String toString() {
        return toString(null);
    }

    public String toString(String matchedTarget) {
        MapBuilder mb = new MapBuilder();

        if (matchedTarget != null) {
            mb.append("target", matchedTarget);
        }

        if (user != null) {
            mb.append("user", user);
        }

        if (role != null) {
            mb.append("role", role);
        }

        mb.append("allow", allowedNames);
        mb.append("deny", deniedNames);

        return new ToStringBuilder().append("Grant").append(mb).toString();
    }
}
