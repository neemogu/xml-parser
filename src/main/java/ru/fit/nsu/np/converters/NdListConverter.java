package ru.fit.nsu.np.converters;

import ru.fit.nsu.np.openmap.dao.NdBean;

import javax.persistence.Converter;

@Converter
public class NdListConverter extends BeanListConverter<NdBean> {}
