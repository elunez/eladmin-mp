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
package me.zhengjie.modules.invitationcode.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.invitationcode.domain.UserInvitationCodeBind;
import me.zhengjie.modules.invitationcode.service.UserInvitationCodeBindService;
import me.zhengjie.modules.invitationcode.domain.vo.UserInvitationCodeBindQueryCriteria;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.zhengjie.utils.PageResult;

/**
* @author John Lee
* @date 2024-06-12
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "邀请码管理")
@RequestMapping("/api/userInvitationCodeBind")
public class UserInvitationCodeBindController {

    private final UserInvitationCodeBindService userInvitationCodeBindService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('userInvitationCodeBind:list')")
    public void exportUserInvitationCodeBind(HttpServletResponse response, UserInvitationCodeBindQueryCriteria criteria) throws IOException {
        userInvitationCodeBindService.download(userInvitationCodeBindService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询邀请码")
    @ApiOperation("查询邀请码")
    @PreAuthorize("@el.check('userInvitationCodeBind:list')")
    public ResponseEntity<PageResult<UserInvitationCodeBind>> queryUserInvitationCodeBind(UserInvitationCodeBindQueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(userInvitationCodeBindService.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增邀请码")
    @ApiOperation("新增邀请码")
    @PreAuthorize("@el.check('userInvitationCodeBind:add')")
    public ResponseEntity<Object> createUserInvitationCodeBind(@Validated @RequestBody UserInvitationCodeBind resources){
        userInvitationCodeBindService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改邀请码")
    @ApiOperation("修改邀请码")
    @PreAuthorize("@el.check('userInvitationCodeBind:edit')")
    public ResponseEntity<Object> updateUserInvitationCodeBind(@Validated @RequestBody UserInvitationCodeBind resources){
        userInvitationCodeBindService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除邀请码")
    @ApiOperation("删除邀请码")
    @PreAuthorize("@el.check('userInvitationCodeBind:del')")
    public ResponseEntity<Object> deleteUserInvitationCodeBind(@RequestBody List<Long> ids) {
        userInvitationCodeBindService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}