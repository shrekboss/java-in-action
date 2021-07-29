package org.crayzer.design.design_mode_pattern.behavioural.template.sample02;

import java.sql.ResultSet;

public interface RowMapper<T> {

    T mapRow(ResultSet rs, int rowNum) throws Exception;

}
