package com.yy.dao;

import com.yy.domain.entity.Card;

public interface CardDao {
    int deleteByPrimaryKey(Long cardID);

    int insert(Card record);

    int insertSelective(Card record);

    Card selectByPrimaryKey(Long cardID);

    int updateByPrimaryKeySelective(Card record);

    int updateByPrimaryKey(Card record);
}