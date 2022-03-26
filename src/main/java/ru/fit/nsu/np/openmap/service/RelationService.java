package ru.fit.nsu.np.openmap.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fit.nsu.np.openmap.bean.RelationBean;
import ru.fit.nsu.np.openmap.dao.RelationEntity;
import ru.fit.nsu.np.openmap.repository.RelationRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class RelationService extends BaseService<RelationEntity, RelationBean, RelationRepository> {
    public RelationService(RelationRepository repository) {
        super(repository);
    }

    private static final String[] ignoredFields = new String[] {"timestamp"};

    @Override
    public RelationBean loadBean(RelationEntity entity) {
        RelationBean bean = new RelationBean();
        BeanUtils.copyProperties(entity, bean, ignoredFields);
        bean.setTimestamp(entity.getTimestamp() == null ? null : entity.getTimestamp().toEpochSecond(ZoneOffset.UTC));
        return bean;
    }

    @Transactional
    public RelationBean create(RelationBean bean) {
        RelationEntity relation = new RelationEntity();
        BeanUtils.copyProperties(bean, relation, ignoredFields);

        relation.setId(null);
        relation.setUser("Admin");
        relation.setUid(1L);
        relation.setVersion(1L);
        relation.setTimestamp(LocalDateTime.now());

        return loadBean(save(relation));
    }

    @Transactional
    public RelationBean update(RelationBean bean) {
        RelationEntity found = findOne(bean.getId());
        if (found == null) {
            return null;
        }
        found.setMembers(bean.getMembers());
        found.setTags(bean.getTags());
        found.setChangeset(bean.getChangeset());
        found.setTimestamp(LocalDateTime.now());
        found.setVersion(found.getVersion() + 1);
        found.setVisible(bean.getVisible());

        return loadBean(save(found));
    }

    @Transactional
    public void delete(Long id) {
        RelationEntity found = findOne(id);
        if (found != null) {
            delete(found);
        }
    }
}
