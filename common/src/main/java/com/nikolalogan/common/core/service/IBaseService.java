package com.nikolalogan.common.core.service;

/**
 * @author: yuxuanting
 * @description:
 * @date: 2021-02-20 09:18
 */

import com.nikolalogan.common.core.controller.response.Resp;
import com.nikolalogan.common.core.dto.BaseDto;
import com.nikolalogan.common.core.dto.page.PageInfo;
import com.nikolalogan.common.core.dto.page.PageResult;
import com.nikolalogan.common.core.reponsitory.entity.BaseEntity;

import java.util.List;

/**
 * 基础服务方法（基于jpa）
 *
 * @param <DTO>
 * @param <ENTITY>
 */
public interface IBaseService<DTO extends BaseDto, ENTITY extends BaseEntity> {

//    /**
//     * 新增
//     *
//     * @param dto
//     * @return
//     */
//    DTO add(DTO dto);

//    /**
//     * 修改
//     *
//     * @param dto
//     * @return
//     */
//    DTO modify(DTO dto);

    /**
     * 编辑（新增、修改）
     *
     * @param dto
     * @return
     */
    Resp edit(DTO dto);

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    DTO getById(String id);

    /**
     * 根据自定key唯一查询
     *
     * @param keyName
     * @param keyValue
     * @return
     */
    DTO getByKey(String keyName, Object keyValue);

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    Resp logicDeleteById(String id);

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    Resp deleteById(String id);

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    PageResult page(PageInfo pageInfo, DTO dto);

    /**
     * 列表查询
     *a
     * @param dto
     * @return
     */
    List<DTO> list(DTO dto);


}

