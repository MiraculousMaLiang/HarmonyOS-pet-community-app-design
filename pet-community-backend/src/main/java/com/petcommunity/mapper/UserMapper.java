package com.petcommunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petcommunity.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
