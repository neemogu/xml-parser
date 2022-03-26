package ru.fit.nsu.np.openmap.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class OsmPersistentEntity {

    @Id
    @Column(name = "id")
    protected Long id;

    @Column(name = "\"user\"")
    protected String user;

    @Column(name = "uid")
    protected Long uid;

    @Column(name = "visible")
    protected Boolean visible;

    @Column(name = "version")
    protected Long version;

    @Column(name = "changeset")
    protected Long changeset;

    @Column(name = "timestamp")
    protected LocalDateTime timestamp;
}
