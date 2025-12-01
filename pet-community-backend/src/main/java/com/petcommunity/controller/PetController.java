package com.petcommunity.controller;

import com.petcommunity.common.result.Result;
import com.petcommunity.dto.PetDTO;
import com.petcommunity.entity.Pet;
import com.petcommunity.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 宠物Controller
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
@Tag(name = "宠物档案管理", description = "宠物信息管理相关接口")
@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @Operation(summary = "添加宠物")
    @PostMapping
    public Result<Pet> addPet(
            @RequestParam Long userId,
            @Valid @RequestBody PetDTO petDTO) {
        Pet pet = petService.addPet(userId, petDTO);
        return Result.success(pet);
    }

    @Operation(summary = "获取宠物列表")
    @GetMapping("/list")
    public Result<List<Pet>> getPetList(@RequestParam Long userId) {
        List<Pet> pets = petService.getPetListByUserId(userId);
        return Result.success(pets);
    }

    @Operation(summary = "获取宠物详情")
    @GetMapping("/{petId}")
    public Result<Pet> getPetDetail(@PathVariable Long petId) {
        Pet pet = petService.getPetById(petId);
        return Result.success(pet);
    }

    @Operation(summary = "更新宠物信息")
    @PutMapping("/{petId}")
    public Result<String> updatePet(
            @PathVariable Long petId,
            @Valid @RequestBody PetDTO petDTO) {
        petDTO.setPetId(petId);
        boolean success = petService.updatePet(petDTO);
        return success ? Result.success("更新成功") : Result.error("更新失败");
    }

    @Operation(summary = "删除宠物")
    @DeleteMapping("/{petId}")
    public Result<String> deletePet(@PathVariable Long petId) {
        boolean success = petService.deletePet(petId);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }

}
