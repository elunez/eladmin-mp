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
package me.zhengjie.modules.mnt.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.mnt.domain.Database;
import me.zhengjie.modules.mnt.mapper.DatabaseMapper;
import me.zhengjie.modules.mnt.service.DatabaseService;
import me.zhengjie.modules.mnt.domain.vo.DatabaseQueryCriteria;
import me.zhengjie.modules.mnt.util.SqlUtils;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageResult;
import me.zhengjie.utils.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseServiceImpl extends ServiceImpl<DatabaseMapper, Database> implements DatabaseService {

    private final DatabaseMapper databaseMapper;

    @Override
    public PageResult<Database> queryAll(DatabaseQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(databaseMapper.findAll(criteria, page));
    }

    @Override
    public List<Database> queryAll(DatabaseQueryCriteria criteria){
        return databaseMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Database resources) {
        resources.setId(IdUtil.simpleUUID());
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Database resources) {
        Database database = getById(resources.getId());
        database.copy(resources);
        saveOrUpdate(database);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<String> ids) {
        removeBatchByIds(ids);
    }

	@Override
	public boolean testConnection(Database resources) {
		try {
			return SqlUtils.testConnection(resources.getJdbcUrl(), resources.getUserName(), resources.getPwd());
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

    @Override
    public void download(List<Database> databases, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Database database : databases) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("数据库名称", database.getName());
            map.put("数据库连接地址", database.getJdbcUrl());
            map.put("用户名", database.getUserName());
            map.put("创建日期", database.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
