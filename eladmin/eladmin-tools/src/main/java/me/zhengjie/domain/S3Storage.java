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
package me.zhengjie.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import me.zhengjie.base.BaseEntity;

/**
* @description /
* @author Zheng Jie
* @date 2025-06-19
**/
@Data
@TableName("tool_s3_storage")
@EqualsAndHashCode(callSuper = true)
public class S3Storage extends BaseEntity implements Serializable {

    @TableId(value = "storage_id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @NotBlank
    @ApiModelProperty(value = "真实存储的名称")
    private String fileRealName;

    @NotBlank
    @ApiModelProperty(value = "文件大小")
    private String fileSize;

    @NotBlank
    @ApiModelProperty(value = "文件MIME 类型")
    private String fileMimeType;

    @NotBlank
    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @NotBlank
    @ApiModelProperty(value = "文件路径")
    private String filePath;

    public void copy(S3Storage source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
