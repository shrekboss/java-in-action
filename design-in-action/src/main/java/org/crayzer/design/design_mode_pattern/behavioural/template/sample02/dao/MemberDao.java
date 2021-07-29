package org.crayzer.design.design_mode_pattern.behavioural.template.sample02.dao;

import org.crayzer.design.design_mode_pattern.behavioural.template.sample02.JdbcTemplate;
import org.crayzer.design.design_mode_pattern.behavioural.template.sample02.RowMapper;
import org.crayzer.design.design_mode_pattern.behavioural.template.sample02.entity.Member;

import java.sql.ResultSet;
import java.util.List;

public class MemberDao {
    //为什么不继承，主要是为了解耦
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(null);

    public List<?> query(){
        String sql = "select * from t_member";
        return jdbcTemplate.executeQuery(sql,new RowMapper<Member>(){
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws Exception {
                Member member = new Member();
                member.setUsername(rs.getString("username"));
                member.setPassword(rs.getString("password"));
                member.setAge(rs.getInt("age"));
                member.setAddr(rs.getString("addr"));
                return member;
            }
        },null);
    }
}
