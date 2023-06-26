<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${package}.mapper.${className}Mapper">
    <#if columns??>
    <resultMap id="BaseResultMap" type="${package}.domain.${className}">
        <#list columns as column>
            <#if column.columnKey = 'PRI'>
        <id column="${column.columnName}" property="${column.changeColumnName}"/>
            </#if>
            <#if column.columnKey != 'PRI'>
        <result column="${column.columnName}" property="${column.changeColumnName}"/>
            </#if>
        </#list>
    </resultMap>

    <sql id="Base_Column_List">
        <#list columns as column>${column.columnName}<#if column_has_next>, </#if></#list>
    </sql>
    </#if>

    <select id="findAll" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${tableName}
        <#if queryColumns??>
        <where>
        <#list queryColumns as column>
            <if test="criteria.${column.changeColumnName} != null">
            <#if column.queryType = '='>
                and ${column.columnName} = ${symbol}{criteria.${column.changeColumnName}}
            </#if>
            <#if column.queryType = 'Like'>
                and ${column.columnName} like concat('%',${symbol}{criteria.${column.changeColumnName}},'%')
            </#if>
            <#if column.queryType = '!='>
                and ${column.columnName} != ${symbol}{criteria.${column.changeColumnName}}
            </#if>
            <#if column.queryType = 'NotNull'>
                and ${column.columnName} is not null
            </#if>
            <#if column.queryType = '>='>
                and ${column.columnName} &gt;= ${symbol}{criteria.${column.changeColumnName}}
            </#if>
            <#if column.queryType = '<='>
                and ${column.columnName} &lt;= ${symbol}{criteria.${column.changeColumnName}}
            </#if>
            </if>
        </#list>
        <#if betweens??>
            <#list betweens as column>
            <if test="criteria.${column.changeColumnName} != null and criteria.${column.changeColumnName}.size() > 0">
                AND ${column.columnName} BETWEEN ${symbol}{criteria.${column.changeColumnName}[0]} AND ${symbol}{criteria.${column.changeColumnName}[1]}
            </if>
            </#list>
        </#if>
        </where>
        </#if>
        <#if pkIdName != 'none'>
        order by ${pkIdName} desc
        </#if>
    </select>
</mapper>