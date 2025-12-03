package com.petcommunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petcommunity.entity.LikeRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 点赞记录Mapper接口
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
@Mapper
public interface LikeRecordMapper extends BaseMapper<LikeRecord> {

}
