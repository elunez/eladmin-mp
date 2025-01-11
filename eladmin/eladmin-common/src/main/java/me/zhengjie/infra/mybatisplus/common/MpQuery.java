package me.zhengjie.infra.mybatisplus.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询描述
 *
 * @date 2023-11-25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MpQuery {
    /**
     * 基本对象的属性名
     */
    String propName() default "";

    /**
     * 查询方式
     */
    MpQueryTypeEnum type() default MpQueryTypeEnum.EQUAL;

    /**
     * 连接查询的属性名, 如User类中的dept
     */
    String joinName() default "";

    /**
     * 默认左连接
     */
    Join join() default Join.LEFT;

    /**
     * 多字段模糊搜索, 仅支持String类型字段, 多个用逗号隔开, 如@MpQuery(blurry = "email,username")
     */
    String blurry() default "";

    /**
     * 适用于简单连接查询, 复杂的请自定义该注解, 或者使用sql查询
     */
    enum Join {
        LEFT, RIGHT, INNER
    }
}
