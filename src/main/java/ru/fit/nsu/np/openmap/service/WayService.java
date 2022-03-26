package ru.fit.nsu.np.openmap.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fit.nsu.np.openmap.bean.WayBean;
import ru.fit.nsu.np.openmap.dao.WayEntity;
import ru.fit.nsu.np.openmap.repository.WayRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class WayService extends BaseService<WayEntity, WayBean, WayRepository> {
    public WayService(WayRepository repository) {
        super(repository);
    }

    private static final String[] ignoredFields = new String[] {"timestamp"};

    @Override
    public WayBean loadBean(WayEntity entity) {
        WayBean bean = new WayBean();
        BeanUtils.copyProperties(entity, bean, ignoredFields);
        bean.setTimestamp(entity.getTimestamp() == null ? null : entity.getTimestamp().toEpochSecond(ZoneOffset.UTC));
        return bean;
    }

    @Transactional
    public WayBean create(WayBean bean) {
        WayEntity way = new WayEntity();
        BeanUtils.copyProperties(bean, way, ignoredFields);

        way.setId(null);
        way.setUser("Admin");
        way.setUid(1L);
        way.setVersion(1L);
        way.setTimestamp(LocalDateTime.now());

        return loadBean(save(way));
    }

    @Transactional
    public WayBean update(WayBean bean) {
        WayEntity found = findOne(bean.getId());
        if (found == null) {
            return null;
        }
        found.setNds(bean.getNds());
        found.setTags(bean.getTags());
        found.setChangeset(bean.getChangeset());
        found.setTimestamp(LocalDateTime.now());
        found.setVersion(found.getVersion() + 1);
        found.setVisible(bean.getVisible());

        return loadBean(save(found));
    }

    @Transactional
    public void delete(Long id) {
        WayEntity found = findOne(id);
        if (found != null) {
            delete(found);
        }
    }
}
