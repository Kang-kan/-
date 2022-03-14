package com.markerhub.service;

import com.markerhub.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.markerhub.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2022-03-02
 */
public interface BlogService extends IService<Blog> {

    List<Blog> selectHome(Long id);

    void updateHomeOne(User blog);
}
