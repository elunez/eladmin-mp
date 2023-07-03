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
package me.zhengjie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.zhengjie.domain.LocalStorage;
import me.zhengjie.domain.vo.LocalStorageQueryCriteria;
import me.zhengjie.utils.PageResult;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
public interface LocalStorageService extends IService<LocalStorage> {

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param page     分页参数
     * @return /
     */
    PageResult<LocalStorage> queryAll(LocalStorageQueryCriteria criteria, Page<Object> page);

    /**
     * 查询全部数据
     * @param criteria 条件
     * @return /
     */
    List<LocalStorage> queryAll(LocalStorageQueryCriteria criteria);

    /**
     * 上传
     * @param name 文件名称
     * @param file 文件
     * @return /
     */
    LocalStorage create(String name, MultipartFile file);

    /**
     * 编辑
     * @param resources 文件信息
     */
    void update(LocalStorage resources);

    /**
     * 多选删除
     * @param ids /
     */
    void deleteAll(Long[] ids);

    /**
     * 导出数据
     * @param localStorages 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<LocalStorage> localStorages, HttpServletResponse response) throws IOException;
}