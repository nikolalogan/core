package com.nikolalogan.common.core.reponsitory.dao.impl;


import com.nikolalogan.common.core.reponsitory.dao.BaseDao;
import com.nikolalogan.common.core.reponsitory.entity.BaseEntity;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

/**
 * JpaRepository 扩展实现
 *
 * @EnableJpaRepositories(repositoryBaseClass = BaseDaoImpl.class）
 */

public class BaseDaoImpl<T extends BaseEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseDao<T, ID> {

    private EntityManager em;

    private JpaEntityInformation<T, ID> entityInformation;

    //https://zhuanlan.zhihu.com/p/93158723
    //https://github.com/querydsl/querydsl/tree/master/querydsl-jpa
    private QuerydslPredicateExecutor<T> queryDslJpaPredicateExecutor;

    //JPAQueryFactory
    //private JPAQuery jpaQuery;

    public BaseDaoImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    public BaseDaoImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
        this.queryDslJpaPredicateExecutor =
                new QuerydslJpaPredicateExecutor<T>(JpaEntityInformationSupport.getEntityInformation(domainClass, em),
                        em, SimpleEntityPathResolver.INSTANCE, getRepositoryMethodMetadata());
        //this.jpaQuery = new JPAQuery(em);
    }

    /**
     * 更新时，加入更新时间
     * (non-Javadoc)
     *
     * @see org.springframework.data.repository.CrudRepository#save(Object)
     */
    @Transactional(rollbackFor = Exception.class)
    public <S extends T> S saveOrUpdate(S entity) {
        if (entityInformation.isNew(entity)) {
            entity.setCreateTime(new Date());
            em.persist(entity);
            return entity;
        } else {
            entity.setUpdateTime(new Date());
            return em.merge(entity);
        }
    }

    /**
     * 重写getOne
     *
     * @param id
     * @return
     */
    @Override
    public T getOne(ID id) {

        Assert.notNull(id, "The given id must not be null!");
        Optional<T> entityOptional = this.findById(id);
        if (entityOptional.isEmpty()) {
            return null;
        }
        return entityOptional.get();
    }

    //实现QuerydslPredicateExecutor接口
    @Override
    public Optional<T> findOne(Predicate predicate) {
        return this.queryDslJpaPredicateExecutor.findOne(predicate);
    }

    @Override
    public Iterable<T> findAll(Predicate predicate) {
        return this.queryDslJpaPredicateExecutor.findAll(predicate);
    }

    @Override
    public Iterable<T> findAll(Predicate predicate, Sort sort) {
        return this.queryDslJpaPredicateExecutor.findAll(predicate, sort);
    }

    @Override
    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        return this.queryDslJpaPredicateExecutor.findAll(predicate, pageable);
    }

    @Override
    public long count(Predicate predicate) {
        return this.queryDslJpaPredicateExecutor.count(predicate);
    }

    @Override
    public boolean exists(Predicate predicate) {
        return this.queryDslJpaPredicateExecutor.exists(predicate);
    }

    @Override
    public Iterable<T> findAll(OrderSpecifier[] orders) {
        return this.queryDslJpaPredicateExecutor.findAll(orders);
    }

    @Override
    public Iterable<T> findAll(Predicate predicate, OrderSpecifier[] orders) {
        return this.queryDslJpaPredicateExecutor.findAll(predicate, orders);
    }
}
