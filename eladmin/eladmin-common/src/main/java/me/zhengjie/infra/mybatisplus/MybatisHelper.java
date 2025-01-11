package me.zhengjie.infra.mybatisplus;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.base.BaseEntity;
import me.zhengjie.constant.ElConstant;
import me.zhengjie.infra.exception.BadRequestException;
import me.zhengjie.infra.mybatisplus.common.MpDataScope;
import me.zhengjie.infra.mybatisplus.common.MpFilterUser;
import me.zhengjie.infra.mybatisplus.common.MpQuery;
import me.zhengjie.utils.SecurityUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 查询条件创建工具
 */
@Slf4j
public class MybatisHelper {
    /**
     * 分割器
     */
    private static final Splitter splitter = Splitter.on(",").trimResults().omitEmptyStrings();

    public static <R, Q> QueryWrapper<R> build(Q query) {
        QueryWrapper<R> queryWrapper = new QueryWrapper<>();
        if (query == null) {
            return queryWrapper;
        }
        Class<?> clazz = query.getClass();
        // 数据权限验证
        // isAnnotationPresent: 判断类上有没有注解，或类中的成员变量有没有注解
        // boolean exist = clazz.isAnnotationPresent(MpDataScope.class);
        MpDataScope mds = clazz.getAnnotation(MpDataScope.class);
        if (mds != null) {
            // 管理员放行
            if (!ElConstant.ADMIN.equals(SecurityUtils.getCurrentUsername())) {
                // List<Long> dataScopes = dataService.getDeptIds(userService.findByName(SecurityUtils.getCurrentUsername()));
                // 获取数据权限
                List<Long> dataScopes = SecurityUtils.getCurrentUserDataScope();
                if (CollUtil.isEmpty(dataScopes)) {
                    // 无权限
                    dataScopes.add(-100L);
                }
                handleInQuery(queryWrapper, mds.joinName(), dataScopes);
            }
        }
        try {
            List<Field> fields = getAllFields(clazz, new ArrayList<>());
            for (Field field : fields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                    MpQuery q = field.getAnnotation(MpQuery.class);
                    if (q != null) {
                        String propName = q.propName();
                        String blurry = q.blurry();
                        String attributeName = StrUtil.isBlank(propName) ? field.getName() : propName;
                        attributeName = humpToUnderline(attributeName);
                        Object fieldVal = field.get(query);
                        if (ObjectUtil.isNull(fieldVal) || "".equals(fieldVal)) {
                            continue;
                        }
                        if (ObjectUtil.isNotEmpty(blurry)) {
                            handleBlurryQuery(queryWrapper, blurry, fieldVal);
                            continue;
                        }
                        handleWrapper(q, queryWrapper, attributeName, fieldVal);
                    }
                    // 用户数据权限
                    MpFilterUser filterUser = field.getAnnotation(MpFilterUser.class);
                    if (filterUser != null) {
                        String currentUsername;
                        try {
                            currentUsername = SecurityUtils.getCurrentUsername();
                            if (ElConstant.ADMIN.equals(currentUsername)) {
                                continue;
                            }
                            queryWrapper.eq(field.getName(), currentUsername);
                        } catch (Exception e) {
                            log.warn("用户未登录, @MpFilterUser注解失效, {}", ExceptionUtil.stacktraceToString(e));
                        }
                    }
                    field.setAccessible(false);
                }
            }
        } catch (Exception e) {
            log.error("组装SQL失败", e);
        }
        return queryWrapper;
    }

    private static <R> void handleWrapper(MpQuery q, QueryWrapper<R> queryWrapper, String attributeName, Object fieldVal) {
        switch (q.type()) {
            case EQUAL:
                queryWrapper.eq(attributeName, fieldVal);
                break;
            case GREATER_THAN:
                queryWrapper.ge(attributeName, fieldVal);
                break;
            case LESS_THAN:
                queryWrapper.le(attributeName, fieldVal);
                break;
            case LESS_THAN_NQ:
                queryWrapper.lt(attributeName, fieldVal);
                break;
            case INNER_LIKE:
                queryWrapper.like(attributeName, fieldVal);
                break;
            case LEFT_LIKE:
                queryWrapper.likeLeft(attributeName, fieldVal);
                break;
            case RIGHT_LIKE:
                queryWrapper.likeRight(attributeName, fieldVal);
                break;
            case IN:
                handleInQuery(queryWrapper, attributeName, fieldVal);
                break;
            case NOT_IN:
                handleNotInQuery(queryWrapper, attributeName, fieldVal);
                break;
            case NOT_EQUAL:
                queryWrapper.ne(attributeName, fieldVal);
                break;
            case NOT_NULL:
                queryWrapper.isNotNull(attributeName);
                break;
            case IS_NULL:
                queryWrapper.isNull(attributeName);
                break;
            case BETWEEN:
                handleBetweenQuery(queryWrapper, fieldVal, attributeName);
                break;
            default:
                break;
        }
    }

    private static <R> void handleBetweenQuery(QueryWrapper<R> queryWrapper, Object fieldVal, String finalAttributeName) {
        if (fieldVal instanceof List) {
            List<Object> between = new ArrayList<>((List<?>) fieldVal);
            if (CollectionUtil.isNotEmpty(between)) {
                if (between.size() >= 2) {
                    queryWrapper.between(finalAttributeName, between.get(0), between.get(1));
                } else {
                    throw new BadRequestException("BETWEEN类型的对象列表长度必须 >= 2");
                }
            }
        } else {
            throw new BadRequestException("BETWEEN类型的对象必须是一个List子集合");
        }
    }

    private static <R> void handleNotInQuery(QueryWrapper<R> queryWrapper, String finalAttributeName, Object fieldVal) {
        Collection<?> wrapNotInVal = (Collection<?>) fieldVal;
        if (CollectionUtil.isNotEmpty(wrapNotInVal)) {
            Optional<?> anyValOptional = wrapNotInVal.stream().findAny();
            if (anyValOptional.isPresent()) {
                Object o = anyValOptional.get();
                if (o instanceof Long) {
                    queryWrapper.notIn(finalAttributeName, fieldVal);
                } else if (o instanceof Integer) {
                    queryWrapper.notIn(finalAttributeName, fieldVal);
                } else {
                    throw new BadRequestException("NOT_IN类型的对象属性值必须是Long/Integer类型集合");
                }
            }
        }
    }

    /**
     * IN查询
     *
     * @param queryWrapper       /
     * @param fieldVal           /
     * @param finalAttributeName /
     * @param <R>                /
     */
    private static <R> void handleInQuery(QueryWrapper<R> queryWrapper, String finalAttributeName, Object fieldVal) {
        Collection<?> wrapInVal = (Collection<?>) fieldVal;
        if (CollectionUtil.isNotEmpty(wrapInVal)) {
            Optional<?> anyValOptional = wrapInVal.stream().findAny();
            if (anyValOptional.isPresent()) {
                Object o = anyValOptional.get();
                if (o instanceof Long) {
                    queryWrapper.in(finalAttributeName, fieldVal);
                } else if (o instanceof Integer) {
                    queryWrapper.in(finalAttributeName, fieldVal);
                } else {
                    throw new BadRequestException("IN类型的对象属性值必须是Long/Integer类型集合");
                }
            }
        }
    }

    /**
     * 模糊多字段
     *
     * @param queryWrapper /
     * @param blurry       /
     * @param fieldVal     /
     * @param <R>          /
     */
    private static <R> void handleBlurryQuery(QueryWrapper<R> queryWrapper, String blurry, Object fieldVal) {
        List<String> blurryList = splitter.splitToList(blurry);
        queryWrapper.and(wrapper -> {
            for (String blurryItem : blurryList) {
                String column = humpToUnderline(blurryItem);
                wrapper.or();
                wrapper.like(column, fieldVal.toString());
            }
        });
    }

    public static List<Field> getAllFields(Class<?> clazz, List<Field> fields) {
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }

    /***
     * 驼峰命名转为下划线命名
     *
     * @param para
     *        驼峰命名的字符串
     */
    private static String humpToUnderline(String para) {
        return StrUtil.toUnderlineCase(para);
    }

    @TableName("test_domain")
    private static class TestDomain {
        @NotNull(groups = BaseEntity.Update.class)
        @TableId(value = "user_id", type = IdType.AUTO)
        private Long id;
        @TableField(value = "dept_id")
        private Long deptId;
        @NotBlank
        private String username;
        @NotBlank
        private String nickName;
    }

    public static void main(String[] args) {
        QueryWrapper<TestDomain> query = new QueryWrapper<TestDomain>();
        //query.or();
        query.or(wrapper -> wrapper.eq("username", 1).or().eq("nickname", 2));
        //query.like("a",1);
        //query.or();
        //query.like("b",2);
        //query.and(wrapper->wrapper.eq("c",1));
        query.eq("id", 1);
        query.orderByDesc("id");

//        query.apply("left join student t1 on t1.id = t1.id");
//        query.last("left join student t1 on t1.id = t1.id");

        System.err.println("getSqlSelect=================================");
        System.err.println(query.getSqlSelect());
        System.err.println("getSqlSegment=================================");
        System.err.println(query.getSqlSegment());
        System.err.println("getSqlComment=================================");
        System.err.println(query.getSqlComment());
        System.err.println("getSqlSet=================================");
        System.err.println(query.getSqlSet());
        System.err.println("getTargetSql=================================");
        System.err.println(query.getTargetSql());
    }
}
