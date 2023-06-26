/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.zhengjie.modules.system.domain.Menu;
import me.zhengjie.modules.system.domain.vo.MenuQueryCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2023-06-20
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> findAll(@Param("criteria") MenuQueryCriteria criteria);

    LinkedHashSet<Menu> findByRoleIdsAndTypeNot(@Param("roleIds") Set<Long> roleIds, @Param("type") Integer type);

    List<Menu> findByPidIsNullOrderByMenuSort();

    List<Menu> findByPidOrderByMenuSort(@Param("pid") Long pid);

    @Select("SELECT menu_id id FROM sys_menu WHERE title = #{title}")
    Menu findByTitle(@Param("title") String title);

    @Select("SELECT menu_id id FROM sys_menu WHERE name = #{name}")
    Menu findByComponentName(@Param("name") String name);

    @Select("SELECT count(*) FROM sys_menu WHERE pid = #{pid}")
    int countByPid(@Param("pid") Long pid);

    @Select("update sys_menu set sub_count = #{count} where menu_id = #{menuId} ")
    void updateSubCntById(@Param("count") int count, @Param("menuId") Long menuId);
}
