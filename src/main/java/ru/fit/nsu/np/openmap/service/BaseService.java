package ru.fit.nsu.np.openmap.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.fit.nsu.np.openmap.bean.EntityBean;
import ru.fit.nsu.np.openmap.dao.PersistentEntity;
import ru.fit.nsu.np.openmap.repository.EntityRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class BaseService<T extends PersistentEntity, B extends EntityBean, R extends EntityRepository<T>> {

    @Getter
    protected final R repository;

    @Transactional(readOnly = true)
    public T findOne(Long id) {
        return id == null ? null : repository.findById(id).orElse(null);
    }

    @Transactional
    public List<T> save(Iterable<T> entities) {
        return repository.saveAll(entities);
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<T> findAll(Iterable<Long> ids) {
        return repository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    @Transactional
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    public abstract B loadBean(T entity);

    @Transactional(readOnly = true)
    public List<B> loadAll() {
        return repository.findAll().stream().map(this::loadBean).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<B> loadAll(Iterable<Long> ids) {
        return repository.findAllById(ids).stream().map(this::loadBean).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public B loadOne(Long id) {
        return id == null ? null : repository.findById(id).map(this::loadBean).orElse(null);
    }

    public List<B> loadAll(Pageable pageable) {
        return repository.findAll(pageable).getContent().stream().map(this::loadBean).collect(Collectors.toList());
    }

    protected void setCreateFields(T entity) {
        entity.setId(null);
        entity.setUser("Admin");
        entity.setUid(1L);
        entity.setVersion(1L);
        entity.setTimestamp(LocalDateTime.now());
    }

    protected void setUpdateFields(T entity, B bean) {
        entity.setChangeset(bean.getChangeset());
        entity.setTimestamp(LocalDateTime.now());
        entity.setVersion(entity.getVersion() + 1);
        entity.setVisible(bean.getVisible());
    }
}
