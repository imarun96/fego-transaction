package com.fego.transaction.common.base;

import com.fego.transaction.common.base.specification.IdSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

public class BaseRepositoryImpl<T extends BaseModel> extends SimpleJpaRepository<T, Long> implements BaseRepository<T> {

    private final EntityManager entityManager;
    @Autowired
    private IdSpecifications idSpecifications;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> findOne(Specification<T> specification) {
        Specification<T> baseSpec = idSpecifications.notDeleted().and(specification);
        return super.findOne(baseSpec);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return super.findAll(idSpecifications.notDeleted(), pageable);
    }

    @Override
    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        Specification<T> baseSpec = idSpecifications.notDeleted().and(specification);
        return super.findAll(baseSpec, pageable);
    }

    @Override
    public void delete(T entity) {
        entity.setDeleted(true);
        super.save(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        for (T entity : entities) {
            delete(entity);
        }
    }

    @Override
    public long count() {
        return super.count(idSpecifications.notDeleted());
    }

    @Override
    public long count(Specification<T> spec) {
        return super.count(idSpecifications.notDeleted().and(spec));
    }

    @Override
    @Transactional
    public void refresh(T t) {
        entityManager.refresh(t);
    }
}