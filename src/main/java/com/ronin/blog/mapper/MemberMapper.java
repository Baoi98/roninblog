package com.ronin.blog.mapper;

import com.ronin.blog.entity.Member;
import java.util.List;

public interface MemberMapper {
    int deleteByPrimaryKey(Integer memberId);

    int insert(Member record);

    Member selectByPrimaryKey(Integer memberId);

    List<Member> selectAll();

    int updateByPrimaryKey(Member record);
}