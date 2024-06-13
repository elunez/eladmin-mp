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
package me.zhengjie.modules.uniapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.zhengjie.modules.invitationcode.domain.UserInvitationCodeBind;
import me.zhengjie.modules.invitationcode.service.UserInvitationCodeBindService;
import me.zhengjie.modules.uniapp.domain.UniCustomer;
import me.zhengjie.utils.*;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.uniapp.service.UniCustomerService;
import me.zhengjie.modules.uniapp.domain.vo.UniCustomerQueryCriteria;
import me.zhengjie.modules.uniapp.mapper.UniCustomerMapper;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @description 服务实现
* @author John Lee
* @date 2024-06-13
**/
@Service
@RequiredArgsConstructor
public class UniCustomerServiceImpl extends ServiceImpl<UniCustomerMapper, UniCustomer> implements UniCustomerService {

    private final UniCustomerMapper uniCustomerMapper;
    private final UserInvitationCodeBindService bindService;

    @Override
    public PageResult<UniCustomer> queryAll(UniCustomerQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(uniCustomerMapper.findAll(
                userVerification(criteria)//验证当前用户是否可以查看全部客户信息
                , page));
    }

    @Override
    public List<UniCustomer> queryAll(UniCustomerQueryCriteria criteria){
        return uniCustomerMapper.findAll(
                userVerification(criteria)//验证当前用户是否可以查看全部客户信息
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UniCustomer resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UniCustomer resources) {
        UniCustomer uniCustomer = getById(resources.getCustomerId());
        uniCustomer.copy(resources);
        saveOrUpdate(uniCustomer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<UniCustomer> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UniCustomer uniCustomer : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("客户账号", uniCustomer.getCustomerNum());
            map.put("客户名称", uniCustomer.getCustomerName());
            map.put("邀请码", uniCustomer.getInvitationCode());
            map.put("密码", uniCustomer.getPassword());
            map.put("创建日期", uniCustomer.getCreateTime());
            map.put("更新时间", uniCustomer.getUpdateTime());
            map.put("创建人", uniCustomer.getCreateBy());
            map.put("最后更新人", uniCustomer.getUpdateBy());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * @description: 如果不是超级管理员，只允许查看自己所有的客户信息
     * @param: queryCriteria
     * @returns: me.zhengjie.modules.uniapp.domain.vo.UniCustomerQueryCriteria
     * @auther: John Lee
     * @date: 2024/6/13 16:48
     */
    private UniCustomerQueryCriteria userVerification(UniCustomerQueryCriteria queryCriteria){
        if (!"admin".equals(SecurityUtils.getCurrentUsername())){
            QueryWrapper<UserInvitationCodeBind> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",SecurityUtils.getCurrentUserId());
            UserInvitationCodeBind one = bindService.getOne(queryWrapper, false);
            if (Objects.isNull(one)){
                queryCriteria.setInvitationCode("");
            }else {
                queryCriteria.setInvitationCode(one.getInvitationCode());
            }

        }
        return queryCriteria;
    }
}