package com.nikolalogan.common.core.reponsitory.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author: yuxuanting
 * @description:
 * @date: 2021-02-20 14:59
 */
@Setter
@Getter
@Entity
@Table(name = "file")
public class FileEntity extends BaseEntity{
    /**
     * 文件名
     */
    @Column
    String fileName;

    /**
     * 原始名称
     */
    @Column
    String originalName;

    /**
     * 后缀名
     */
    @Column
    String suffix;

    /**
     * 文件路径
     */
    @Column
    String filePath;
}
