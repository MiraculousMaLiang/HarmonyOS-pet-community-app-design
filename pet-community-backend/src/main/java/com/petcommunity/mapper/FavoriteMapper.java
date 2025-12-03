package com.petcommunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petcommunity.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;

/**
 * 收藏Mapper接口
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {

}
