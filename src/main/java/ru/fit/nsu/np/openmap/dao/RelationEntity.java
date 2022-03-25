package ru.fit.nsu.np.openmap.dao;

import lombok.Getter;
import lombok.Setter;
import ru.fit.nsu.np.converters.MemberListConverter;
import ru.fit.nsu.np.converters.TagHStoreConverter;
import ru.fit.nsu.np.jaxb.Relation;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "relation")
public class RelationEntity extends OsmPersistentEntity {

    @Convert(converter = MemberListConverter.class)
    @Column(name = "members")
    private List<MemberBean> members;

    @Convert(converter = TagHStoreConverter.class)
    @Column(name = "tags")
    private List<TagBean> tags;

    public static RelationEntity fromXml(Relation xmlObject) {
        RelationEntity entity = new RelationEntity();
        entity.setId(xmlObject.getId() == null ? null : xmlObject.getId().longValue());
        entity.setChangeset(xmlObject.getChangeset() == null ? null : xmlObject.getChangeset().longValue());
        entity.setTimestamp(xmlObject.getTimestamp() == null ? null :
                xmlObject.getTimestamp().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
        entity.setUid(xmlObject.getUid() == null ? null : xmlObject.getUid().longValue());
        entity.setVersion(xmlObject.getVersion() == null ? null : xmlObject.getVersion().longValue());
        entity.setUser(xmlObject.getUser());
        entity.setVisible(xmlObject.isVisible());
        entity.setTags(xmlObject.getTag().stream().map(TagBean::fromXml).collect(Collectors.toList()));
        entity.setMembers(xmlObject.getMember().stream().map(MemberBean::fromXml).collect(Collectors.toList()));
        return entity;
    }
}
