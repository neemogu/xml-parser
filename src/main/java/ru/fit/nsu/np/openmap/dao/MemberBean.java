package ru.fit.nsu.np.openmap.dao;

import lombok.Getter;
import lombok.Setter;
import ru.fit.nsu.np.jaxb.Member;

@Getter
@Setter
public class MemberBean {
    private String type;
    private Long ref;
    private String role;

    public static MemberBean fromXml(Member xmlObject) {
        MemberBean bean = new MemberBean();
        bean.setRef(xmlObject.getRef() == null ? null : xmlObject.getRef().longValue());
        bean.setType(xmlObject.getType());
        bean.setRole(xmlObject.getRole());
        return bean;
    }
}
