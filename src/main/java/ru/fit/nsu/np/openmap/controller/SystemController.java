package ru.fit.nsu.np.openmap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fit.nsu.np.xml.jaxb.NodeListJaxbProcessor;
import ru.fit.nsu.np.xml.jaxb.RelationListJaxbProcessor;
import ru.fit.nsu.np.xml.jaxb.WayListJaxbProcessor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/system")
public class SystemController {

    private final NodeListJaxbProcessor nodeListJaxbProcessor;
    private final WayListJaxbProcessor wayListJaxbProcessor;
    private final RelationListJaxbProcessor relationListJaxbProcessor;

    @GetMapping("/load-data/node")
    public String loadNodeData() {
        nodeListJaxbProcessor.processFile("RU-NVS.osm.bz2");
        return "Node data from file RU-NVS.osm.bz2 has been loaded";
    }

    @GetMapping("/load-data/way")
    public String loadWayData() {
        wayListJaxbProcessor.processFile("RU-NVS.osm.bz2");
        return "Way data from file RU-NVS.osm.bz2 has been loaded";
    }
    @GetMapping("/load-data/relation")
    public String loadRelationData() {
        relationListJaxbProcessor.processFile("RU-NVS.osm.bz2");
        return "Relation data from file RU-NVS.osm.bz2 has been loaded";
    }
}
