package com.petcommunity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.petcommunity.common.exception.BusinessException;
import com.petcommunity.common.result.ResultCode;
import com.petcommunity.dto.PetDTO;
import com.petcommunity.entity.Pet;
import com.petcommunity.mapper.PetMapper;
import com.petcommunity.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 宠物Service实现类
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetMapper petMapper;

    @Override
    public Pet addPet(Long userId, PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setUserId(userId);
        pet.setStatus(1); // 正常状态

        petMapper.insert(pet);
        return pet;
    }

    @Override
    public List<Pet> getPetListByUserId(Long userId) {
        LambdaQueryWrapper<Pet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Pet::getUserId, userId)
                .eq(Pet::getStatus, 1)
                .orderByDesc(Pet::getCreateTime);
        return petMapper.selectList(wrapper);
    }

    @Override
    public Pet getPetById(Long petId) {
        Pet pet = petMapper.selectById(petId);
        if (pet == null || pet.getStatus() == 0) {
            throw new BusinessException(ResultCode.PET_NOT_EXIST);
        }
        return pet;
    }

    @Override
    public boolean updatePet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return petMapper.updateById(pet) > 0;
    }

    @Override
    public boolean deletePet(Long petId) {
        Pet pet = getPetById(petId);
        pet.setStatus(0); // 标记为删除
        return petMapper.updateById(pet) > 0;
    }

}
