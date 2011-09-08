package com.alibaba.sample.petstore.common.auth.impl;

import static com.alibaba.citrus.util.ArrayUtil.*;
import static com.alibaba.citrus.util.CollectionUtil.*;
import static com.alibaba.citrus.util.ObjectUtil.*;
import static com.alibaba.citrus.util.StringUtil.*;
import static java.util.Collections.*;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;

import com.alibaba.citrus.service.AbstractService;
import com.alibaba.citrus.util.ObjectUtil;
import com.alibaba.sample.petstore.common.auth.PageAuthorizationService;

/**
 * 为页面授权的service。
 * 
 * @author Michael Zhou
 */
public class PageAuthorizationServiceImpl extends AbstractService<PageAuthorizationService> implements
        PageAuthorizationService {
    /** MATCH_EVERYTHING代表所有用户和role，但不包含匿名用户 */
    public final static String MATCH_EVERYTHING = "*";

    /** 特列用户名：匿名用户 */
    public final static String ANONYMOUS_USER = "anonymous";

    private final List<AuthMatch> matches = createLinkedList();

    public void setMatches(AuthMatch[] matches) {
        this.matches.clear();

        if (matches != null) {
            for (AuthMatch match : matches) {
                this.matches.add(match);
            }
        }
    }

    public boolean isAllow(String target, String userName, String[] roleNames, String action) {
        userName = defaultIfNull(trimToNull(userName), ANONYMOUS_USER);

        if (action != null && !action.startsWith("/")) {
            action = "/" + action;
        }

        String roleNameStr = null;
        String actionStr = null;

        if (getLogger().isDebugEnabled()) {
            roleNameStr = ObjectUtil.toString(roleNames);
            actionStr = action;
        }

        // 找出所有匹配的pattern，按匹配长度倒排序。
        MatchResult[] results = getMatchResults(target);

        if (isEmptyArray(results)) {
            if (getLogger().isDebugEnabled()) {
                logDebug("Access denied: no patterns matched", target, userName, roleNameStr, actionStr);
            }

            return false;
        }

        // 按顺序检查授权，直到role或user被allow或deny
        for (MatchResult result : results) {
            AuthMatch match = result.match;

            for (AuthGrant grant : match.getGrants()) {
                String grantUser = grant.getUser();
                String grantRole = grant.getRole();

                // 判断user或role是否匹配
                boolean userMatch = false;
                boolean roleMatch = false;

                if (grantUser != null) {
                    // 排除匿名用户
                    userMatch = grantUser.equals(MATCH_EVERYTHING) && !ANONYMOUS_USER.equals(userName)
                            || grantUser.equals(userName);
                }

                if (grantRole != null) {
                    // 排除匿名用户
                    roleMatch = grantRole.equals(MATCH_EVERYTHING) && !ANONYMOUS_USER.equals(userName)
                            || arrayContains(roleNames, grantRole);
                }

                if (!userMatch && !roleMatch) {
                    continue;
                }

                // 判断action是否匹配
                boolean actionAllowed = grant.isActionAllowed(action);
                boolean actionDenied = grant.isActionDenied(action);

                if (!actionAllowed && !actionDenied) {
                    continue;
                }

                boolean allowed = !actionDenied;

                if (getLogger().isDebugEnabled()) {
                    if (allowed) {
                        logDebug("Access permitted: " + match, target, userName, roleNameStr, actionStr);
                    } else {
                        logDebug("Access denied: " + match, target, userName, roleNameStr, actionStr);
                    }
                }

                return allowed;
            }
        }

        // 默认为拒绝
        if (getLogger().isDebugEnabled()) {
            logDebug("Access denied: user or role has not be authorized", target, userName, roleNameStr, actionStr);
        }

        return false;
    }

    public MatchResult[] getMatchResults(String target) {
        List<MatchResult> results = createArrayList(matches.size());

        // 匹配所有，注意，这里按倒序匹配，这样长度相同的匹配，以后面的为准。
        for (ListIterator<AuthMatch> i = matches.listIterator(matches.size()); i.hasPrevious();) {
            AuthMatch match = i.previous();
            Matcher matcher = match.getTargetPattern().matcher(target);

            if (matcher.find()) {
                MatchResult result = new MatchResult();
                result.matchLength = matcher.end() - matcher.start();
                result.match = match;

                results.add(result);
            }
        }

        // 按匹配长度倒排序，注意，这是稳定排序，对于长度相同的匹配，顺序不变。
        sort(results);

        // 除去重复的匹配
        Map<AuthGrant[], MatchResult> grantsSet = createLinkedHashMap();

        for (MatchResult result : results) {
            AuthGrant[] grants = result.match.getGrants();

            if (!grantsSet.containsKey(grants)) {
                grantsSet.put(grants, result);
            }
        }

        return grantsSet.values().toArray(new MatchResult[grantsSet.size()]);
    }

    /**
     * 记录debug日志。
     */
    private void logDebug(String message, String target, String userName, String roleNameStr, String actionStr) {
        getLogger().debug("{}: target=\"{}\", user=\"{}\", roles=\"{}\", action=\"{}\"",
                new Object[] { message, target, userName, roleNameStr, actionStr });
    }

    private static class MatchResult implements Comparable<MatchResult> {
        private int matchLength = -1;
        private AuthMatch match;

        public int compareTo(MatchResult o) {
            return o.matchLength - matchLength;
        }
    }
}
