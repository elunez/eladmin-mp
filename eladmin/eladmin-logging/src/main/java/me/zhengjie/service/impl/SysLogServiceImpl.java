/*
 *  Copyright 2019-2025 Zheng Jie
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
package me.zhengjie.service.impl;

import cn.hutool.core.lang.Dict;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import me.zhengjie.domain.SysLog;
import me.zhengjie.mapper.SysLogMapper;
import me.zhengjie.service.SysLogService;
import me.zhengjie.domain.dto.SysLogQueryCriteria;
import me.zhengjie.utils.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    private final SysLogMapper sysLogMapper;
    // 定义敏感字段常量数组
    private static final String[] SENSITIVE_KEYS = {"password"};

    @Override
    public PageResult<SysLog> queryAll(SysLogQueryCriteria criteria, Page<SysLog> page) {
        return PageUtil.toPage(sysLogMapper.queryAll(criteria, page));
    }

    @Override
    public List<SysLog> queryAll(SysLogQueryCriteria criteria) {
        return sysLogMapper.queryAll(criteria);
    }

    @Override
    public PageResult<SysLog> queryAllByUser(SysLogQueryCriteria criteria, Page<SysLog> page) {
        return PageUtil.toPage(sysLogMapper.queryAllByUser(criteria, page));
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SysLog sysLog) {
        if (sysLog == null) {
            throw new IllegalArgumentException("Log 不能为 null!");
        }

        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        me.zhengjie.annotation.Log aopLog = method.getAnnotation(me.zhengjie.annotation.Log.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        // 获取参数
        JSONObject params = getParameter(method, joinPoint.getArgs());

        // 填充基本信息
        sysLog.setRequestIp(ip);
        sysLog.setAddress(StringUtils.getCityInfo(sysLog.getRequestIp()));
        sysLog.setMethod(methodName);
        sysLog.setUsername(username);
        sysLog.setParams(JSON.toJSONString(params));
        sysLog.setBrowser(browser);
        sysLog.setDescription(aopLog.value());

        // 如果没有获取到用户名，尝试从参数中获取
        if(StringUtils.isBlank(sysLog.getUsername())){
            sysLog.setUsername(params.getString("username"));
        }

        // 保存
        save(sysLog);
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private JSONObject getParameter(Method method, Object[] args) {
        JSONObject params = new JSONObject();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            // 过滤掉 MultiPartFile
            if (args[i] instanceof MultipartFile) {
                continue;
            }
            // 过滤掉 HttpServletResponse
            if (args[i] instanceof HttpServletResponse) {
                continue;
            }
            // 过滤掉 HttpServletRequest
            if (args[i] instanceof HttpServletRequest) {
                continue;
            }
            // 将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                params.putAll((JSONObject) JSON.toJSON(args[i]));
            } else {
                String key = parameters[i].getName();
                params.put(key, args[i]);
            }
        }
        // 遍历敏感字段数组并替换值
        Set<String> keys = params.keySet();
        for (String key : SENSITIVE_KEYS) {
            if (keys.contains(key)) {
                params.put(key, "******");
            }
        }
        // 返回参数
        return params;
    }

    @Override
    public Object findByErrDetail(Long id) {
        String details = sysLogMapper.getExceptionDetails(id);
        return Dict.create().set("exception", details);
    }

    @Override
    public void download(List<SysLog> sysLogs, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysLog sysLog : sysLogs) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户名", sysLog.getUsername());
            map.put("IP", sysLog.getRequestIp());
            map.put("IP来源", sysLog.getAddress());
            map.put("描述", sysLog.getDescription());
            map.put("浏览器", sysLog.getBrowser());
            map.put("请求耗时/毫秒", sysLog.getTime());
            map.put("异常详情", sysLog.getExceptionDetail());
            map.put("创建日期", sysLog.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByError() {
        // 删除 ERROR 级别的日志
        sysLogMapper.deleteByLevel("ERROR");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByInfo() {
        // 删除 INFO 级别的日志
        sysLogMapper.deleteByLevel("INFO");
    }
}
