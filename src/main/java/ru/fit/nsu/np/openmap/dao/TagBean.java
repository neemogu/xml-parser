package ru.fit.nsu.np.openmap.dao;

import lombok.Getter;
import lombok.Setter;
import ru.fit.nsu.np.jaxb.Tag;

@Getter
@Setter
public class TagBean {
    private String k;
    private String v;

    public static TagBean fromXml(Tag xml) {
        TagBean bean = new TagBean();
        bean.setK(xml.getK());
        bean.setV(xml.getV());
        return bean;
    }
}
