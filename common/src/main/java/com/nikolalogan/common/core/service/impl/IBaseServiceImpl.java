package com.nikolalogan.common.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.nikolalogan.common.core.controller.response.Resp;
import com.nikolalogan.common.core.dto.BaseDto;
import com.nikolalogan.common.core.dto.page.PageInfo;
import com.nikolalogan.common.core.dto.page.PageResult;
import com.nikolalogan.common.core.reponsitory.dao.BaseDao;
import com.nikolalogan.common.core.reponsitory.entity.BaseEntity;
import com.nikolalogan.common.core.service.IBaseService;
import com.nikolalogan.common.core.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 基础服务方法实现（基于jpa）
 * 注意使用规则：
 * 1. 这个只针对单表操作（提供增删改查基础功能，方便dto与entity转换），带条件查询时，请覆盖相应方法
 * 2. dtoToEntity方法子类一定要覆盖实现（先判断dto中属性值是否为空，再set到entity）
 * 3. 子类不用再注入Entity对应Dao，请使用this.entityDao
 * 4. 多表操作，请自定义service方法，操作对应实体
 *
 * @param <DTO>
 * @param <ENTITY>
 * @see IBaseService
 */
@Slf4j
public abstract class IBaseServiceImpl<DAO extends BaseDao, DTO extends BaseDto, ENTITY extends BaseEntity> implements IBaseService<DTO, ENTITY> {

    @Autowired
    protected DAO entityDao;

    private Class<DAO> entityDaoClass;
    private Class<ENTITY> entityClass;
    private Class<DTO> entityDtoClass;

    public IBaseServiceImpl() {
        // 处理泛型类型 得到具体的class
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            this.entityDaoClass = (Class<DAO>) ((ParameterizedType) type).getActualTypeArguments()[0];
            this.entityDtoClass = (Class<DTO>) ((ParameterizedType) type).getActualTypeArguments()[1];
            this.entityClass = (Class<ENTITY>) ((ParameterizedType) type).getActualTypeArguments()[2];
            log.info("BaseServiceImpl->当前操作Dao[{}],Dto[{}],Entity[{}]", new Object[]{this.entityDaoClass, this.entityDtoClass.getName(), this.entityClass.getName()});
        } else {
            log.error("不支持的泛型类型!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp edit(DTO dto) {
        this.editBefore(dto);
        if (ObjectUtil.isNotEmpty(dto.getId())) {
            return this.modify(dto);
        } else {
            return this.add(dto);
        }
    }

    @Override
    public DTO getById(String id) {
        Assert.notNull(id, "[" + this.entityClass.getName() + "]->id is not null");
        Optional<ENTITY> entityOptional = this.entityDao.findById(id);
        if (entityOptional.isEmpty()) {
            return null;
        }
        ENTITY entity = entityOptional.get();
        DTO dto = ReflectUtil.newInstance(this.entityDtoClass);
        this.entityToDto(entity, dto);
        return dto;
    }

    @Override
    public DTO getByKey(String keyName, Object keyValue) {
        Optional<ENTITY> entityOptional = this.entityDao.findOne(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.where(criteriaBuilder.equal(root.get(keyName), keyValue));
                return criteriaQuery.getRestriction();
            }
        });
        if (entityOptional.isEmpty()) {
            return null;
        }
        ENTITY entity = entityOptional.get();
        DTO dto = ReflectUtil.newInstance(this.entityDtoClass);
        this.entityToDto(entity, dto);
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp logicDeleteById(String id) {
        Assert.notNull(id, "id is not null");
        ENTITY entity = (ENTITY) this.entityDao.getOne(id);
        entity.setIsDelete(true);
        this.entityDao.saveOrUpdate(entity);
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp deleteById(String id) {
        Assert.notNull(id, "id is not null");
        this.entityDao.deleteById(id);
        return R.success();

    }

    @Override
    public List<DTO> list(DTO dto) {
        return this.entityToDto(this.entityDao.findAll(getListSpecification(dto)));
    }

    @Override
    public PageResult page(PageInfo pageInfo, DTO dto) {
        PageRequest pageRequest = PageRequest.of(pageInfo.getPage() - 1, pageInfo.getLimit());
        Page page = this.entityDao.findAll(this.getPageSpecification(dto), pageRequest);
        return PageResult.builder()
                .total(page.getTotalElements())
                .rows(this.entityToDto(page.getContent()))
                .build();

    }

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    private Resp add(DTO dto) {
        ENTITY entity = ReflectUtil.newInstance(this.entityClass);
        this.dtoToEntity(dto, entity);
        this.entityDao.saveOrUpdate(entity);
        //后置处理（保证dto数据不变）
        this.editAfter(dto, entity);
        // 返回id
        dto.setId(String.valueOf(entity.getId()));
        return R.success();
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    private Resp modify(DTO dto) {
        Assert.notNull(dto.getId(), "id is not null");
        ENTITY entity = (ENTITY) this.entityDao.getOne(dto.getId());
        this.dtoToEntity(dto, entity);
        this.entityDao.saveOrUpdate(entity);
        this.editAfter(dto, entity);
        return R.success();
    }

    /**
     * entity to dto
     *
     * @param entityList
     * @return
     */
    private List<DTO> entityToDto(List<ENTITY> entityList) {
        if (ObjectUtil.isEmpty(entityList)) {
            return new ArrayList<DTO>(0);
        }
        List<DTO> dtoList = new ArrayList<DTO>(entityList.size());
        DTO dto = null;
        for (ENTITY entity : entityList) {
            dto = ReflectUtil.newInstance(this.entityDtoClass);
            this.entityToDto(entity, dto);
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * 编辑前处理（如果出现这种业务操作，建议自定义service方法，操作实体处理）
     *
     * @param dto
     */
    protected void editBefore(DTO dto) {
        //子类按需扩展
    }

    /**
     * 编辑后处理（如果出现这种业务操作，建议自定义service方法，操作实体处理）
     *
     * @param dto
     * @param entity
     */
    protected void editAfter(DTO dto, ENTITY entity) {
        //子类按需扩展
    }

    /**
     * 列表查询条件（针对单表）
     *
     * @param dto
     * @return
     */
    protected Specification<ENTITY> getListSpecification(DTO dto) {
        //子类按需扩展
        return null;
    }

    /**
     * 分页查询条件（针对单表）
     *
     * @param dto
     * @return
     */
    protected Specification<ENTITY> getPageSpecification(DTO dto) {
        //子类按需扩展
        return null;
    }

    /**
     * dto to entity（建议每个都重写此方法）
     *
     * @param dto
     * @param entity
     */
    protected void dtoToEntity(DTO dto, ENTITY entity) {
        //子类按需扩展
    }

    /**
     * entity to dto（建议先super.entityToDto(xx,xxx), 后加入其他逻辑）
     *
     * @param entity
     * @param dto
     */
    protected void entityToDto(ENTITY entity, DTO dto) {
        //子类按需扩展
        BeanUtil.copyProperties(entity, dto, new String[]{});
    }


}
