package ru.fit.nsu.np.openmap.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class EntityBean {

    protected Long id;
    protected String user;
    protected Long uid;
    protected Boolean visible;
    protected Long version;
    @NotNull(message = "Changeset must be defined")
    @Positive(message = "Changeset must be positive")
    protected Long changeset;
    protected Long timestamp;
}
