package com.petcommunity.service;

import com.petcommunity.dto.PetDTO;
import com.petcommunity.entity.Pet;

import java.util.List;

/**
 * 宠物Service接口
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
public interface PetService {

    /**
     * 添加宠物
     *
     * @param userId 用户ID
     * @param petDTO 宠物信息
     * @return 宠物信息
     */
    Pet addPet(Long userId, PetDTO petDTO);

    /**
     * 获取宠物列表
     *
     * @param userId 用户ID
     * @return 宠物列表
     */
    List<Pet> getPetListByUserId(Long userId);

    /**
     * 获取宠物详情
     *
     * @param petId 宠物ID
     * @return 宠物信息
     */
    Pet getPetById(Long petId);

    /**
     * 更新宠物信息
     *
     * @param petDTO 宠物信息
     * @return 是否成功
     */
    boolean updatePet(PetDTO petDTO);

    /**
     * 删除宠物
     *
     * @param petId 宠物ID
     * @return 是否成功
     */
    boolean deletePet(Long petId);

}
