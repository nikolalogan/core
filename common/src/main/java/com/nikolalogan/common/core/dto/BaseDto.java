package com.nikolalogan.common.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
/**
 * @author: yuxuanting
 * @description:
 * @date: 2021-02-20 09:27
 */

@Setter
@Getter
@NoArgsConstructor
@ToString
public class BaseDto implements Serializable {
    /**
     * dto to entity ignore fields（copy all）
     */
    public final static String[] DTO_TO_ENTITY_IGNORE_FIELDS = new String[]{"id", "isDelete", "createTime", "creator", "updateTime", "updater"};
    /**
     * id
     */
    protected String id;
    /**
     * false or true
     */
    @JsonIgnore
    protected Boolean isDelete;

    /**
     * 创建时间(默认为当前)
     */
    protected Date createTime;
    /**
     * 创建者
     */
    protected String creator;
    /**
     * 更新时间
     */
    protected Date updateTime;
    /**
     * 更新者
     */
    protected String updater;
}
