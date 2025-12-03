package com.petcommunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petcommunity.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论Mapper接口
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
