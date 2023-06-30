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
package me.zhengjie.modules.mnt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.zhengjie.modules.mnt.domain.Database;
import me.zhengjie.modules.mnt.domain.vo.DatabaseQueryCriteria;
import me.zhengjie.utils.PageResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author ZhangHouYing
 * @date 2019-08-24
 */
public interface DatabaseService extends IService<Database> {

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param page     分页参数
     * @return /
     */
    PageResult<Database> queryAll(DatabaseQueryCriteria criteria, Page<Object> page);

    /**
     * 查询全部
     * @param criteria 条件
     * @return /
     */
    List<Database> queryAll(DatabaseQueryCriteria criteria);

    /**
     * 创建
     * @param resources /
     */
    void create(Database resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(Database resources);

    /**
     * 删除
     * @param ids /
     */
    void delete(Set<String> ids);

	/**
	 * 测试连接数据库
	 * @param resources /
	 * @return /
	 */
	boolean testConnection(Database resources);

    /**
     * 导出数据
     * @param queryAll /
     * @param response /
     * @throws IOException e
     */
    void download(List<Database> queryAll, HttpServletResponse response) throws IOException;
}
