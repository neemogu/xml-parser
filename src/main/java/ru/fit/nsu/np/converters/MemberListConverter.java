package ru.fit.nsu.np.converters;

import ru.fit.nsu.np.openmap.dao.MemberBean;

import javax.persistence.Converter;

@Converter
public class MemberListConverter extends BeanListConverter<MemberBean> {}