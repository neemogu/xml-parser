package ru.fit.nsu.np.openmap.dao;

import lombok.Getter;
import lombok.Setter;
import ru.fit.nsu.np.jaxb.Node;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class NodeEntity extends OsmPersistentEntity {
    private List<TagBean> tags;
    protected Double lat;
    protected Double lon;

    public static NodeEntity fromXmlObject(Node xmlObject) {
        NodeEntity entity = new NodeEntity();
        entity.setId(xmlObject.getId() == null ? null : xmlObject.getId().longValue());
        entity.setChangeset(xmlObject.getChangeset() == null ? null : xmlObject.getChangeset().longValue());
        entity.setTimestamp(xmlObject.getTimestamp() == null ? null : xmlObject.getTimestamp().toGregorianCalendar().getTime());
        entity.setUid(xmlObject.getUid() == null ? null : xmlObject.getUid().longValue());
        entity.setVersion(xmlObject.getVersion() == null ? null : xmlObject.getVersion().longValue());
        entity.setUser(xmlObject.getUser());
        entity.setVisible(xmlObject.isVisible());
        entity.setLat(xmlObject.getLat());
        entity.setLon(xmlObject.getLon());
        entity.setTags(xmlObject.getTag().stream().map(TagBean::fromXml).collect(Collectors.toList()));
        return entity;
    }
}
