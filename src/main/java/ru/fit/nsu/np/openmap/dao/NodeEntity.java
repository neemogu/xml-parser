package ru.fit.nsu.np.openmap.dao;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLHStoreType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.fit.nsu.np.jaxb.Node;
import ru.fit.nsu.np.jaxb.Tag;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "node")
@TypeDef(name = "hstore", typeClass = PostgreSQLHStoreType.class)
public class NodeEntity extends PersistentEntity {

    @Column(name = "tags")
    @Type(type = "hstore")
    private Map<String, String> tags;

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
        entity.setTags(xmlObject.getTag().stream().collect(Collectors.toMap(Tag::getK, Tag::getV)));
        return entity;
    }
}
