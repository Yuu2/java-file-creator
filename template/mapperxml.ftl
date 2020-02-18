<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="dev.yuu2.springmybatiscrud.mapper.ItemMapper">

    <select id="selectOne" resultType=".${upperClassName}">
        SELECT * from ${defaultClassName} where id = ${r"#{"}sid${r"}"}
    </select>

    <select id="selectAll" resultType=".${upperClassName}">
        select * from ${defaultClassName}
    </select>

    <insert id="insert" useGeneratedKeys="true" keyProperty="id">
        insert into ${defaultClassName} (<#list columns as column><#if column?is_first><#else>${column}<#if column_has_next>, </#if></#if></#list>) values (<#list columns as column><#if column?is_first><#else>${r"#{"}${column.lowerCamel}${r"}"}<#if column_has_next>, </#if></#if></#list>)
    </insert>

    <update id="update">
        update ${defaultClassName} set <#list columns as column><#if column?is_first><#else>${column}=${r"#{"}${column.lowerCamel}${r"}"}<#if column_has_next>, </#if></#if></#list> where id = ${r"#{"}sid${r"}"}
    </update>

    <delete id="delete">
        delete from ${defaultClassName} where id = ${r"#{"}sid${r"}"}
    </delete>

</mapper>