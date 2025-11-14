package com.petcommunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petcommunity.entity.Post;
import org.apache.ibatis.annotations.Mapper;

/**
 * 动态Mapper接口
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

}
