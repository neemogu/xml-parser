package ru.fit.nsu.np.openmap.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fit.nsu.np.openmap.bean.NodeBean;
import ru.fit.nsu.np.openmap.dao.NodeEntity;
import ru.fit.nsu.np.openmap.repository.NodeRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class NodeService extends BaseService<NodeEntity, NodeBean, NodeRepository> {
    public NodeService(NodeRepository repository) {
        super(repository);
    }

    private static final String[] ignoredFields = new String[] {"timestamp", "lat", "lon"};

    @Override
    public NodeBean loadBean(NodeEntity entity) {
        NodeBean bean = new NodeBean();
        BeanUtils.copyProperties(entity, bean, ignoredFields);
        bean.setTimestamp(entity.getTimestamp() == null ? null : entity.getTimestamp().toEpochSecond(ZoneOffset.UTC));
        bean.setLat(entity.getLat() == null ? null : BigDecimal.valueOf(entity.getLat()));
        bean.setLon(entity.getLon() == null ? null : BigDecimal.valueOf(entity.getLon()));
        return bean;
    }

    @Transactional
    public NodeBean create(NodeBean bean) {
        NodeEntity node = new NodeEntity();
        BeanUtils.copyProperties(bean, node, ignoredFields);

        node.setLon(bean.getLon().doubleValue());
        node.setLat(bean.getLat().doubleValue());

        setCreateFields(node);

        return loadBean(save(node));
    }

    @Transactional
    public NodeBean update(NodeBean bean) {
        NodeEntity found = findOne(bean.getId());
        if (found == null) {
            return null;
        }
        found.setLat(bean.getLat().doubleValue());
        found.setLon(bean.getLon().doubleValue());
        found.setTags(bean.getTags());
        setUpdateFields(found, bean);

        return loadBean(save(found));
    }

    @Transactional
    public void delete(Long id) {
        NodeEntity found = findOne(id);
        if (found != null) {
            delete(found);
        }
    }
}
