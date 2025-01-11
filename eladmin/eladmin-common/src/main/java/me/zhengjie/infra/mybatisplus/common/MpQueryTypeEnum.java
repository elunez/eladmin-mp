package me.zhengjie.infra.mybatisplus.common;


/**
 * 动态查询 枚举
 *
 * @date 2021-09-14 09:04
 */
public enum MpQueryTypeEnum {
    /**
     * 相等
     */
    EQUAL
    /**
     * 大于等于
     */
    , GREATER_THAN
    /**
     * 小于等于
     */
    , LESS_THAN
    /**
     * 中模糊查询
     */
    , INNER_LIKE
    /**
     * 左模糊查询
     */
    , LEFT_LIKE
    /**
     * 右模糊查询
     */
    , RIGHT_LIKE
    /**
     * 小于
     */
    , LESS_THAN_NQ
    /**
     * 包含
     */
    , IN
    /**
     * 不包含
     */
    , NOT_IN
    /**
     * 不等于
     */
    , NOT_EQUAL
    /**
     * between(注：被注解的属性一定得是数组或者集合, 长度大于等于2)
     */
    , BETWEEN
    /**
     * 不为空
     */
    , NOT_NULL
    /**
     * 为空
     */
    , IS_NULL
}
