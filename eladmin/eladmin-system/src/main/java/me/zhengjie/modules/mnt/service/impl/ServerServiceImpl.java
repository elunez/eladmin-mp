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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.mnt.domain.Server;
import me.zhengjie.modules.mnt.mapper.DeployServerMapper;
import me.zhengjie.modules.mnt.mapper.ServerMapper;
import me.zhengjie.modules.mnt.service.ServerService;
import me.zhengjie.modules.mnt.domain.vo.ServerQueryCriteria;
import me.zhengjie.modules.mnt.util.ExecuteShellUtil;
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
@Service
@RequiredArgsConstructor
public class ServerServiceImpl extends ServiceImpl<ServerMapper, Server> implements ServerService {

    private final ServerMapper serverMapper;
    private final DeployServerMapper deployServerMapper;

    @Override
    public PageResult<Server> queryAll(ServerQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(serverMapper.findAll(criteria, page));
    }

    @Override
    public List<Server> queryAll(ServerQueryCriteria criteria){
        return serverMapper.findAll(criteria);
    }

    @Override
    public Server findByIp(String ip) {
        return serverMapper.findByIp(ip);
    }

	@Override
	public Boolean testConnect(Server resources) {
		ExecuteShellUtil executeShellUtil = null;
		try {
			executeShellUtil = new ExecuteShellUtil(resources.getIp(), resources.getAccount(), resources.getPassword(),resources.getPort());
			return executeShellUtil.execute("ls")==0;
		} catch (Exception e) {
			return false;
		}finally {
			if (executeShellUtil != null) {
				executeShellUtil.close();
			}
		}
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Server resources) {
		save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Server resources) {
        Server server = getById(resources.getId());
		server.copy(resources);
        saveOrUpdate(server);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        removeBatchByIds(ids);
        // 删除与之关联的服务
        deployServerMapper.deleteByServerIds(ids);
    }

    @Override
    public void download(List<Server> servers, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Server deploy : servers) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("服务器名称", deploy.getName());
            map.put("服务器IP", deploy.getIp());
            map.put("端口", deploy.getPort());
            map.put("账号", deploy.getAccount());
            map.put("创建日期", deploy.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
