package com.markerhub.service.impl;

import com.markerhub.entity.Blog;
import com.markerhub.entity.User;
import com.markerhub.mapper.BlogMapper;
import com.markerhub.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2022-03-02
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    BlogMapper blogMapper;
    @Override
    public List<Blog> selectHome(Long id) {
        return blogMapper.selectHome(id);
    }

    @Override
    public void updateHomeOne(User blog) {
        blogMapper.updateHomeOne(blog);
    }
}
