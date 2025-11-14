package com.petcommunity.utils;

import com.petcommunity.common.exception.BusinessException;
import com.petcommunity.common.result.ResultCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 安全工具类
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
public class SecurityUtils {

    /**
     * 获取当前登录用户名
     *
     * @return 用户名
     */
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        }

        throw new BusinessException(ResultCode.UNAUTHORIZED);
    }

    /**
     * 获取当前登录用户ID (从用户名解析，需要在Service层转换)
     *
     * @return 用户ID
     */
    public static Long getCurrentUserId() {
        // 这里需要根据用户名查询用户ID，在实际使用时应该在Service层实现
        // 为了简化，这里返回null，实际使用时需要注入UserService
        return null;
    }

    /**
     * 获取Authentication对象
     *
     * @return Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
