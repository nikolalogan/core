package com.nikolalogan.common.core.reponsitory.dao;

import com.nikolalogan.common.core.reponsitory.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author: yuxuanting
 * @description:
 * @date: 2020-09-01 23:19
 */
@NoRepositoryBean
public interface BaseDao<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> ,QuerydslPredicateExecutor {
    <S extends T> S saveOrUpdate(S entity);
}
