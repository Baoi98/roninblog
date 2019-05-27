package com.ronin.blog.mapper;

import com.ronin.blog.entity.Notice;
import java.util.List;

public interface NoticeMapper {
    int deleteByPrimaryKey(Integer noticeId);

    int insert(Notice record);

    Notice selectByPrimaryKey(Integer noticeId);

    List<Notice> selectAll();

    int updateByPrimaryKey(Notice record);

    /**
     * 查询最新的通告
     * @return
     */
    Notice findNewNotice();
}