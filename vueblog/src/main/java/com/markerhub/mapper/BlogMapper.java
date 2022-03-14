package com.markerhub.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.markerhub.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2022-03-02
 */
public interface BlogMapper extends BaseMapper<Blog> {

    @Select("select * from m_blog where user_id = #{id}")
    List<Blog> selectHome(Long id);

    @Update("update m_user set username = #{uer.username},email = #{uer.email},password = #{uer.password} where id = #{user.id}")
    void updateHomeOne(User user);
}
