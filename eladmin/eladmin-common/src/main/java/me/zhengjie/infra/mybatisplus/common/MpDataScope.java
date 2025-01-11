package me.zhengjie.infra.mybatisplus.common;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限 注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MpDataScope {
    /**
     * Entity 中与部门关联的字段名称
     */
    String joinName() default "";
}