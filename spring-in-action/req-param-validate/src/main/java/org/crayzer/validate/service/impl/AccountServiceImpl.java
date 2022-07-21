package org.crayzer.validate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crayzer.validate.common.BusinessException;
import org.crayzer.validate.mapper.AccountMapper;
import org.crayzer.validate.model.Account;
import org.crayzer.validate.service.AccountService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;

    @Override
    public Account getById(int id) {
        return accountMapper.selectById(id);
    }

    @Override
    public int insert(Account account) {
        int rs = accountMapper.insert(account);
        if (rs <= 0) {
            log.warn("插入账户记录失败: " + account);
            throw new BusinessException("插入失败");
        }
        // 返回新生成的 id
        return account.getId();
    }

}
