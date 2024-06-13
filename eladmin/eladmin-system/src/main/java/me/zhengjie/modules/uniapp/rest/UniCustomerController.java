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
package me.zhengjie.modules.uniapp.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.uniapp.domain.UniCustomer;
import me.zhengjie.modules.uniapp.service.UniCustomerService;
import me.zhengjie.modules.uniapp.domain.vo.UniCustomerQueryCriteria;
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
* @date 2024-06-13
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "客户信息管理")
@RequestMapping("/api/uniCustomer")
public class UniCustomerController {

    private final UniCustomerService uniCustomerService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('uniCustomer:list')")
    public void exportUniCustomer(HttpServletResponse response, UniCustomerQueryCriteria criteria) throws IOException {
        uniCustomerService.download(uniCustomerService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询客户信息")
    @ApiOperation("查询客户信息")
    @PreAuthorize("@el.check('uniCustomer:list')")
    public ResponseEntity<PageResult<UniCustomer>> queryUniCustomer(UniCustomerQueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(uniCustomerService.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增客户信息")
    @ApiOperation("新增客户信息")
    @PreAuthorize("@el.check('uniCustomer:add')")
    public ResponseEntity<Object> createUniCustomer(@Validated @RequestBody UniCustomer resources){
        uniCustomerService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改客户信息")
    @ApiOperation("修改客户信息")
    @PreAuthorize("@el.check('uniCustomer:edit')")
    public ResponseEntity<Object> updateUniCustomer(@Validated @RequestBody UniCustomer resources){
        uniCustomerService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除客户信息")
    @ApiOperation("删除客户信息")
    @PreAuthorize("@el.check('uniCustomer:del')")
    public ResponseEntity<Object> deleteUniCustomer(@RequestBody List<Long> ids) {
        uniCustomerService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}