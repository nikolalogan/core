package com.nikolalogan.common.core.dto.page;

/**
 * @author: yuxuanting
 * @description:
 * @date: 2021-02-20 09:21
 */

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 分页信息（基于Layui）
 */
@Data
public class PageInfo {
    /**
     * 请求页码（layui）
     */
    @NotNull(message = "页码不能为空")
    private Integer page;
    /**
     * 每页显示条数（layui）
     */
    @NotNull(message = "每页显示数量不能为空")
    private Integer limit;


}

