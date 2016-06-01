package com.yy.dao;

import com.yy.domain.entity.LoanOrder;

public interface LoanOrderDao {
    int deleteByPrimaryKey(String loanordercode);

    int insert(LoanOrder record);

    int insertSelective(LoanOrder record);

    LoanOrder selectByPrimaryKey(String loanordercode);

    int updateByPrimaryKeySelective(LoanOrder record);

    int updateByPrimaryKey(LoanOrder record);
}