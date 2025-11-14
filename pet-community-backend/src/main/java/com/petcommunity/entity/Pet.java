package com.petcommunity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 宠物档案实体类
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
@Data
@TableName("pet")
public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 宠物ID
     */
    @TableId(type = IdType.AUTO)
    private Long petId;

    /**
     * 所属用户ID
     */
    private Long userId;

    /**
     * 宠物名称
     */
    private String petName;

    /**
     * 物种(猫/狗等)
     */
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

    /**
     * 状态(0删除 1正常)
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
