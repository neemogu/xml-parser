package ru.fit.nsu.np.openmap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fit.nsu.np.xml.jaxb.OsmNodeListJaxbProcessor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/system")
public class SystemController {

    private final OsmNodeListJaxbProcessor dataLoader;

    @GetMapping("/load-data")
    public String loadData() {
        dataLoader.processFile("RU-NVS.osm.bz2");
        return "Data from file RU-NVS.osm.bz2 has been loaded";
    }
}
