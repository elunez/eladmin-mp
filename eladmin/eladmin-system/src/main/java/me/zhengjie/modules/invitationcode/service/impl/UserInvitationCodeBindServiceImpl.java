/*
*  Copyright 2019-2023 Zheng Jie
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
package me.zhengjie.modules.invitationcode.service.impl;

import me.zhengjie.modules.invitationcode.domain.UserInvitationCodeBind;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.invitationcode.service.UserInvitationCodeBindService;
import me.zhengjie.modules.invitationcode.domain.vo.UserInvitationCodeBindQueryCriteria;
import me.zhengjie.modules.invitationcode.mapper.UserInvitationCodeBindMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import me.zhengjie.utils.PageUtil;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import me.zhengjie.utils.PageResult;

/**
* @description 服务实现
* @author John Lee
* @date 2024-06-12
**/
@Service
@RequiredArgsConstructor
public class UserInvitationCodeBindServiceImpl extends ServiceImpl<UserInvitationCodeBindMapper, UserInvitationCodeBind> implements UserInvitationCodeBindService {

    private final UserInvitationCodeBindMapper userInvitationCodeBindMapper;

    @Override
    public PageResult<UserInvitationCodeBind> queryAll(UserInvitationCodeBindQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(userInvitationCodeBindMapper.findAll(criteria, page));
    }

    @Override
    public List<UserInvitationCodeBind> queryAll(UserInvitationCodeBindQueryCriteria criteria){
        return userInvitationCodeBindMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserInvitationCodeBind resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserInvitationCodeBind resources) {
        UserInvitationCodeBind userInvitationCodeBind = getById(resources.getBindId());
        userInvitationCodeBind.copy(resources);
        saveOrUpdate(userInvitationCodeBind);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<UserInvitationCodeBind> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserInvitationCodeBind userInvitationCodeBind : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户ID", userInvitationCodeBind.getUserId());
            map.put("客户ID", userInvitationCodeBind.getCustomerId());
            map.put("邀请码", userInvitationCodeBind.getInvitationCode());
            map.put("创建者", userInvitationCodeBind.getCreateBy());
            map.put("更新者", userInvitationCodeBind.getUpdateBy());
            map.put("创建日期", userInvitationCodeBind.getCreateTime());
            map.put("更新时间", userInvitationCodeBind.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}