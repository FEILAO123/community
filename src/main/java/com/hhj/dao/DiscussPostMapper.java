package com.hhj.dao;

import com.hhj.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPost(int userId,int offset,int limit);

    //@Param注解是用于给参数取别名的，如果只有一个参数并且在<if>里使用，则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);
}
