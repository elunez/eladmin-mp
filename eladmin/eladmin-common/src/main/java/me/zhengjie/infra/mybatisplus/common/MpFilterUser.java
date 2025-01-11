package me.zhengjie.infra.mybatisplus.common;

import java.lang.annotation.*;

/**
 * 根据用户名过滤(只能有一个)
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MpFilterUser {
}