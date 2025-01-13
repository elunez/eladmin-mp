package me.zhengjie.utils;

import lombok.*;
import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装类
 * @author Zheng Jie
 * @date 2018-11-23
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    private List<T> content;

    private long totalElements;
}
