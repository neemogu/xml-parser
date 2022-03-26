package ru.fit.nsu.np.openmap.dao;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLHStoreType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.fit.nsu.np.converters.MemberListConverter;
import ru.fit.nsu.np.jaxb.Relation;
import ru.fit.nsu.np.jaxb.Tag;

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
@Table(name = "relation")
@TypeDef(name = "hstore", typeClass = PostgreSQLHStoreType.class)
public class RelationEntity extends PersistentEntity {

    @Convert(converter = MemberListConverter.class)
    @Column(name = "members")
    private List<MemberBean> members;

    @Column(name = "tags")
    @Type(type = "hstore")
    private Map<String, String> tags;

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
        entity.setTags(xmlObject.getTag().stream().collect(Collectors.toMap(Tag::getK, Tag::getV)));
        entity.setMembers(xmlObject.getMember().stream().map(MemberBean::fromXml).collect(Collectors.toList()));
        return entity;
    }
}
