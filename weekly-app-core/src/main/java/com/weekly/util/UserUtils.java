package com.weekly.util;

import com.weekly.pojo.bo.UserBo;
import org.springframework.core.NamedThreadLocal;

import java.util.Optional;

/**
 * @version V1.0
 * @author: csz
 * @Title
 * @Package: com.mountain.common.util
 * @Description:
 * @date: 2019/12/23
 */
public class UserUtils {

    private static final ThreadLocal<UserBo> userBoThreadLocal =
            new NamedThreadLocal<>("CurrentUserBo");

    public static UserBo getCurrentUser() {
        return Optional.ofNullable(userBoThreadLocal.get()).orElseGet(UserBo::new);
    }

    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public static boolean hasUser() {
        return userBoThreadLocal.get() != null;
    }

    public static void setUserBo(UserBo userBo) {
        userBoThreadLocal.set(userBo);
    }

    public static void remove() {
        userBoThreadLocal.remove();
    }

}
