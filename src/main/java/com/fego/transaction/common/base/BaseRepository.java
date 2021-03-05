package com.fego.transaction.common.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@NoRepositoryBean
@RepositoryRestResource(exported = false)
public interface BaseRepository<T extends BaseModel> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
    void refresh(T t);
}