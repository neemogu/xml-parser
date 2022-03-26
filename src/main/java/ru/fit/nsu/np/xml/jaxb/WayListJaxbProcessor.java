package ru.fit.nsu.np.xml.jaxb;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fit.nsu.np.jaxb.Way;
import ru.fit.nsu.np.openmap.dao.WayEntity;
import ru.fit.nsu.np.openmap.service.BaseService;
import ru.fit.nsu.np.openmap.service.WayService;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class WayListJaxbProcessor extends OsmListJaxbProcessor<WayEntity, Way> {

    private final WayService wayService;

    @Override
    protected Class<Way> getXmlClass() {
        return Way.class;
    }

    @Override
    protected BaseService<WayEntity, ?, ?> getService() {
        return wayService;
    }

    @Override
    protected Function<Way, WayEntity> getMappingFunc() {
        return WayEntity::fromXml;
    }
}
