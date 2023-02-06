package com.hhj.service;

import com.hhj.entity.DiscussPost;

import java.util.List;

public interface DiscussPostService {
    //查询帖子
    List<DiscussPost> selectDiscussPost(int userId,int offset,int limit);
    //查询页数
    int DiscussPostRows(int userId);
}
