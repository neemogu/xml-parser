package ru.fit.nsu.np.openmap.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class NodeBean extends EntityBean {

    private Map<String, String> tags;

    @DecimalMin(value = "-90", message = "Latitude is less than -90")
    @DecimalMax(value = "90", message = "Latitude is greater than 90")
    @NotNull(message = "Latitude is not defined")
    protected BigDecimal lat;

    @DecimalMin(value = "-180", message = "Longitude is less than -180")
    @DecimalMax(value = "180", message = "Longitude is greater than 180")
    @NotNull(message = "Longitude is not defined")
    protected BigDecimal lon;
}
