package ru.fit.nsu.np.openmap.dao;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLHStoreType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.fit.nsu.np.converters.NdListConverter;
import ru.fit.nsu.np.jaxb.Tag;
import ru.fit.nsu.np.jaxb.Way;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "way")
@TypeDef(name = "hstore", typeClass = PostgreSQLHStoreType.class)
public class WayEntity extends PersistentEntity {

    @Convert(converter = NdListConverter.class)
    @Column(name = "nds")
    private List<NdBean> nds;

    @Column(name = "tags")
    @Type(type = "hstore")
    private Map<String, String> tags;

    public static WayEntity fromXml(Way xmlObject) {
        WayEntity entity = new WayEntity();
        entity.setId(xmlObject.getId() == null ? null : xmlObject.getId().longValue());
        entity.setChangeset(xmlObject.getChangeset() == null ? null : xmlObject.getChangeset().longValue());
        entity.setTimestamp(xmlObject.getTimestamp() == null ? null :
                xmlObject.getTimestamp().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
        entity.setUid(xmlObject.getUid() == null ? null : xmlObject.getUid().longValue());
        entity.setVersion(xmlObject.getVersion() == null ? null : xmlObject.getVersion().longValue());
        entity.setUser(xmlObject.getUser());
        entity.setVisible(xmlObject.isVisible());
        entity.setTags(xmlObject.getTag().stream().collect(Collectors.toMap(Tag::getK, Tag::getV)));
        entity.setNds(xmlObject.getNd().stream().map(NdBean::fromXml).collect(Collectors.toList()));
        return entity;
    }
}
