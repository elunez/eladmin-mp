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
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.vo.DeptQueryCriteria;
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
public interface DeptMapper extends BaseMapper<Dept> {

    List<Dept> findAll(@Param("criteria") DeptQueryCriteria criteria);

    List<Dept> findByPid(@Param("pid") Long pid);

    List<Dept> findByPidIsNull();

    Set<Dept> findByRoleId(@Param("roleId") Long roleId);

    @Select("select count(*) from sys_dept where pid = #{pid}")
    int countByPid(@Param("pid") Long pid);

    @Select("update sys_dept set sub_count = #{count} where dept_id = #{id}")
    void updateSubCntById(@Param("count") Integer count, @Param("id") Long id);
}