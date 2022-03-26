package ru.fit.nsu.np.openmap.bean;

import lombok.Getter;
import lombok.Setter;
import ru.fit.nsu.np.openmap.dao.MemberBean;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RelationBean extends EntityBean {
    private List<MemberBean> members;
    private Map<String, String> tags;
}
