package ru.fit.nsu.np.openmap.dao;

import lombok.Getter;
import lombok.Setter;
import ru.fit.nsu.np.converters.TagHStoreConverter;
import ru.fit.nsu.np.jaxb.Node;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "node")
public class NodeEntity extends OsmPersistentEntity {

    @Convert(converter = TagHStoreConverter.class)
    @Column(name = "tags")
    private List<TagBean> tags;

    @Column(name = "lat")
    protected Double lat;

    @Column(name = "lon")
    protected Double lon;

    public static NodeEntity fromXmlObject(Node xmlObject) {
        NodeEntity entity = new NodeEntity();
        entity.setId(xmlObject.getId() == null ? null : xmlObject.getId().longValue());
        entity.setChangeset(xmlObject.getChangeset() == null ? null : xmlObject.getChangeset().longValue());
        entity.setTimestamp(xmlObject.getTimestamp() == null ? null :
                xmlObject.getTimestamp().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
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
