package com.petcommunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petcommunity.entity.HealthRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 健康记录Mapper接口
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
@Mapper
public interface HealthRecordMapper extends BaseMapper<HealthRecord> {

}
