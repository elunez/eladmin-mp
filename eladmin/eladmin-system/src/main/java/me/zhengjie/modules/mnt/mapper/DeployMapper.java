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
package me.zhengjie.modules.mnt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.zhengjie.modules.mnt.domain.Deploy;
import me.zhengjie.modules.mnt.domain.vo.DeployQueryCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Set;

/**
 * @author Zheng Jie
 * @description
 * @date 2023-06-12
 **/
@Mapper
public interface DeployMapper extends BaseMapper<Deploy> {

    Long countAll(@Param("criteria") DeployQueryCriteria criteria);

    List<Deploy> findAll(@Param("criteria") DeployQueryCriteria criteria);
    
    Set<Long> getIdByAppIds(@Param("appIds") Set<Long> appIds);

    Deploy getDeployById(@Param("deployId") Long deployId);
}
