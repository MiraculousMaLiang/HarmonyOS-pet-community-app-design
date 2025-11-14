package com.petcommunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petcommunity.entity.Pet;
import org.apache.ibatis.annotations.Mapper;

/**
 * 宠物Mapper接口
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
@Mapper
public interface PetMapper extends BaseMapper<Pet> {

}
