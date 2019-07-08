package com.ronin.blog.mapper;

import com.ronin.blog.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    User selectByPrimaryKey(Integer userId);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User selectByUserEmail(String userEmail);

    User selectByUserName(String userName);

    int changeUserPass(@Param("userEmail") String userEmail,@Param("userPassword") String userPassword);
}