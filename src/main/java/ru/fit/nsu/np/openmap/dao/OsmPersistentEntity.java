package ru.fit.nsu.np.openmap.dao;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public abstract class OsmPersistentEntity {
    protected Long id;
    protected String user;
    protected Long uid;
    protected Boolean visible;
    protected Long version;
    protected Long changeset;
    protected LocalDateTime timestamp;
}
