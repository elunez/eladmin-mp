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
package me.zhengjie.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.*;

/**
 * 分页工具
 * @author Zheng Jie
 * @date 2018-12-10
 */
@SuppressWarnings({"unchecked","all"})
public class PageUtil extends cn.hutool.core.util.PageUtil {

    /**
     * List 分页
     */
    public static List toPage(int page, int size , List list) {
        int fromIndex = page * size;
        int toIndex = page * size + size;
        if(fromIndex > list.size()){
            return new ArrayList();
        } else if(toIndex >= list.size()) {
            return list.subList(fromIndex,list.size());
        } else {
            return list.subList(fromIndex,toIndex);
        }
    }

    /**
     * Page 数据处理
     */
    public static Map<String,Object> toPage(IPage page) {
        Map<String,Object> map = new LinkedHashMap<>(2);
        map.put("content",page.getRecords());
        map.put("totalElements",page.getTotal());
        return map;
    }

    /**
     * 自定义分页
     */
    public static Map<String,Object> toPage(List list) {
        Map<String,Object> map = new LinkedHashMap<>(2);
        map.put("content",list);
        map.put("totalElements",list.size());
        return map;
    }

    /**
     * 返回空数据
     */
    public static Map<String,Object> noData () {
        Map<String,Object> map = new LinkedHashMap<>(2);
        map.put("content",null);
        map.put("totalElements",0);
        return map;
    }

    /**
     * 自定义分页
     */
    public static Map<String,Object> toPage(Object object, Object totalElements) {
        Map<String,Object> map = new LinkedHashMap<>(2);
        map.put("content",object);
        map.put("totalElements",totalElements);
        return map;
    }
}
