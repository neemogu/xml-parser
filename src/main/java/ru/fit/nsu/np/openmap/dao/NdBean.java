package ru.fit.nsu.np.openmap.dao;

import lombok.Getter;
import lombok.Setter;
import ru.fit.nsu.np.jaxb.Nd;

@Setter
@Getter
public class NdBean {
    private Long ref;

    public static NdBean fromXml(Nd xmlObject) {
        NdBean bean = new NdBean();
        bean.setRef(xmlObject.getRef() == null ? null : xmlObject.getRef().longValue());
        return bean;
    }
}
