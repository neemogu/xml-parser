package ru.fit.nsu.np.openmap.bean;

import lombok.Getter;
import lombok.Setter;
import ru.fit.nsu.np.openmap.dao.NdBean;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class WayBean extends EntityBean {
    private List<NdBean> nds;
    private Map<String, String> tags;
}
