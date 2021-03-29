package com.nikolalogan.common.core.dto.page;

/**
 * @author: yuxuanting
 * @description:
 * @date: 2021-02-20 09:22
 */
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *   分页结果
 */
@Setter
@Getter
@ToString
@Builder
public class PageResult {
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 每页数据
     */
    private List<?> rows;
}
