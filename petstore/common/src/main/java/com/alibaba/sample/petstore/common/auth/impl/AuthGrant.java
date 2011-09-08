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
    private String user;
    private String role;
    private Set<String> allowedActionSet = createLinkedHashSet();
    private Set<String> deniedActionSet = createLinkedHashSet();

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

    public Set<String> getAllowedActionSet() {
        return allowedActionSet;
    }

    public void setAllow(String allow) {
        setActionSet(allowedActionSet, allow);
    }

    public Set<String> getDeniedActionSet() {
        return deniedActionSet;
    }

    public void setDeny(String deny) {
        setActionSet(deniedActionSet, deny);
    }

    private void setActionSet(Set<String> actionSet, String names) {
        for (String name : defaultIfNull(split(names, ", "), EMPTY_STRING_ARRAY)) {
            actionSet.add(name);
        }

        if (actionSet.size() > 1 && actionSet.contains(PageAuthorizationServiceImpl.MATCH_EVERYTHING)) {
            actionSet.clear();
            actionSet.add(PageAuthorizationServiceImpl.MATCH_EVERYTHING);
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

        mb.append("allow", allowedActionSet);
        mb.append("deny", deniedActionSet);

        return new ToStringBuilder().append("Grant").append(mb).toString();
    }
}
