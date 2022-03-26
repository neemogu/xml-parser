package ru.fit.nsu.np.xml.jaxb;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fit.nsu.np.jaxb.Relation;
import ru.fit.nsu.np.openmap.dao.RelationEntity;
import ru.fit.nsu.np.openmap.service.BaseService;
import ru.fit.nsu.np.openmap.service.RelationService;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class RelationListJaxbProcessor extends OsmListJaxbProcessor<RelationEntity, Relation> {

    private final RelationService relationService;

    @Override
    protected Class<Relation> getXmlClass() {
        return Relation.class;
    }

    @Override
    protected BaseService<RelationEntity, ?, ?> getService() {
        return relationService;
    }

    @Override
    protected Function<Relation, RelationEntity> getMappingFunc() {
        return RelationEntity::fromXml;
    }
}
