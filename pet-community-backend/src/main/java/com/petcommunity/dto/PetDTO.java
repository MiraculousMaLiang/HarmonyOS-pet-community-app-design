package com.petcommunity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 宠物DTO
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
@Data
public class PetDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 宠物ID
     */
    private Long petId;

    /**
     * 宠物名称
     */
    @NotBlank(message = "宠物名称不能为空")
    private String petName;

    /**
     * 物种(猫/狗等)
     */
    @NotBlank(message = "物种不能为空")
    private String species;

    /**
     * 品种
     */
    private String breed;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /**
     * 性别(0未知 1公 2母)
     */
    private Integer gender;

    /**
     * 体重(kg)
     */
    private BigDecimal weight;

    /**
     * 宠物照片
     */
    private String avatar;

    /**
     * 毛色
     */
    private String color;

    /**
     * 芯片号
     */
    private String chipNo;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 是否绝育(0否 1是)
     */
    private Integer isSterilized;

}
