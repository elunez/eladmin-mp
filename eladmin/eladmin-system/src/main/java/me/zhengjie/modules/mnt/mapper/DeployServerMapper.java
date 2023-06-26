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

import me.zhengjie.modules.mnt.domain.Server;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Set;

/**
 * @author Zheng Jie
 * @description
 * @date 2023-06-12
 **/
@Mapper
public interface DeployServerMapper {

    void insertData(@Param("deployId") Long deployId, @Param("servers") Set<Server> servers);

    void deleteByDeployId(@Param("deployId") Long deployId);

    void deleteByDeployIds(@Param("deployIds") Set<Long> deployIds);

    void deleteByServerIds(@Param("serverIds") Set<Long> serverIds);
}
