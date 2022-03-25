package ru.fit.nsu.np.openmap.dao;

import lombok.Getter;
import lombok.Setter;
import ru.fit.nsu.np.converters.NdListConverter;
import ru.fit.nsu.np.converters.TagHStoreConverter;
import ru.fit.nsu.np.jaxb.Way;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "way")
public class WayEntity extends OsmPersistentEntity {

    @Convert(converter = NdListConverter.class)
    @Column(name = "nds")
    private List<NdBean> nds;

    @Convert(converter = TagHStoreConverter.class)
    @Column(name = "tags")
    private List<TagBean> tags;

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
        entity.setTags(xmlObject.getTag().stream().map(TagBean::fromXml).collect(Collectors.toList()));
        entity.setNds(xmlObject.getNd().stream().map(NdBean::fromXml).collect(Collectors.toList()));
        return entity;
    }
}
