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
package me.zhengjie.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.vo.DeptQueryCriteria;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
public interface DeptService extends IService<Dept> {

    /**
     * 查询所有数据
     * @param criteria 条件
     * @param isQuery /
     * @throws Exception /
     * @return /
     */
    List<Dept> queryAll(DeptQueryCriteria criteria, Boolean isQuery) throws Exception;

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    Dept findById(Long id);

    /**
     * 创建
     * @param resources /
     */
    void create(Dept resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(Dept resources);

    /**
     * 删除
     * @param depts /
     *
     */
    void delete(Set<Dept> depts);

    /**
     * 根据PID查询
     * @param pid /
     * @return /
     */
    List<Dept> findByPid(long pid);

    /**
     * 根据角色ID查询
     * @param id /
     * @return /
     */
    Set<Dept> findByRoleId(Long id);

    /**
     * 导出数据
     * @param depts 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<Dept> depts, HttpServletResponse response) throws IOException;

    /**
     * 获取待删除的部门
     * @param deptList /
     * @param depts /
     * @return /
     */
    Set<Dept> getDeleteDepts(List<Dept> deptList, Set<Dept> depts);

    /**
     * 根据ID获取同级与上级数据
     * @param dept /
     * @param depts /
     * @return /
     */
    List<Dept> getSuperior(Dept dept, List<Dept> depts);

    /**
     * 构建树形数据
     * @param depts /
     * @return /
     */
    Object buildTree(List<Dept> depts);

    /**
     * 获取
     * @param deptList 、
     * @return 、
     */
    List<Long> getDeptChildren(List<Dept> deptList);

    /**
     * 验证是否被角色或用户关联
     * @param depts /
     */
    void verification(Set<Dept> depts);
}