package org.crayzer.validate.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crayzer.validate.model.Account;


@Mapper
public interface AccountMapper {
    /**
     * 通过主键id查询账户
     *
     * @param id 主键
     * @return 账户信息
     */
    Account selectById(int id);

    /**
     * 插入记录
     *
     * @param account 账户实体，插入成功后自动生成的 id 会赋给这个实体的 id 字段
     * @return 影响行数
     */
    int insert(Account account);
}
