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
package me.zhengjie.service;

import me.zhengjie.domain.S3Storage;
import me.zhengjie.domain.dto.S3StorageQueryCriteria;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.zhengjie.utils.PageResult;
import org.springframework.web.multipart.MultipartFile;

/**
* @description 服务接口
* @author Zheng Jie
* @date 2025-06-19
**/
public interface S3StorageService extends IService<S3Storage> {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param page 分页参数
    * @return PageResult
    */
    PageResult<S3Storage> queryAll(S3StorageQueryCriteria criteria, Page<Object> page);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<S3StorageDto>
    */
    List<S3Storage> queryAll(S3StorageQueryCriteria criteria);

    /**
    * 创建
    * @param resources /
    */
    void create(S3Storage resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(List<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<S3Storage> all, HttpServletResponse response) throws IOException;

    /**
     * 私有化下载，仅供参考，还有许多方式
     * @param id 文件ID
     */
    Map<String, String> privateDownload(Long id);

    /**
     * 上传文件
     * @param file 上传的文件
     * @return S3Storage 对象，包含文件存储信息
     */
    S3Storage upload(MultipartFile file);
}