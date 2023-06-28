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
import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.modules.system.domain.vo.RoleQueryCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2023-06-20
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> queryAll();

    Role findById(@Param("roleId") Long roleId);
    Role findByName(@Param("name") String name);

    List<Role> findByUserId(@Param("userId") Long userId);

    Long countAll(@Param("criteria") RoleQueryCriteria criteria);

    List<Role> findAll(@Param("criteria") RoleQueryCriteria criteria);

    int countByDepts(@Param("deptIds") Set<Long> deptIds);

    @Select("SELECT role.role_id as id FROM sys_role role, sys_roles_menus rm " +
            "WHERE role.role_id = rm.role_id AND rm.menu_id = #{menuId}")
    List<Role> findByMenuId(@Param("menuId") Long menuId);
}
