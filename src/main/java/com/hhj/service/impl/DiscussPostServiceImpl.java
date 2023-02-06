package com.hhj.service.impl;


import com.hhj.dao.DiscussPostMapper;
import com.hhj.entity.DiscussPost;
import com.hhj.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;


    //查询帖子
    @Override
    public List<DiscussPost> selectDiscussPost(int userId, int offset, int limit) {
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPost(userId, offset, limit);
        return discussPosts;
    }

    @Override
    public int DiscussPostRows(int userId) {
        int rows = discussPostMapper.selectDiscussPostRows(userId);
        return rows;
    }
}
