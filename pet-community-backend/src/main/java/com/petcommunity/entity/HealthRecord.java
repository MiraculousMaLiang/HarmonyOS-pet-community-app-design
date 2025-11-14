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
 * 健康记录实体类
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
@Data
@TableName("health_record")
public class HealthRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long recordId;

    /**
     * 宠物ID
     */
    private Long petId;

    /**
     * 记录日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate recordDate;

    /**
     * 体重(kg)
     */
    private BigDecimal weight;

    /**
     * 体温(℃)
     */
    private BigDecimal temperature;

    /**
     * 饮食情况
     */
    private String dietInfo;

    /**
     * 运动时长(分钟)
     */
    private Integer exerciseDuration;

    /**
     * 运动类型
     */
    private String exerciseType;

    /**
     * 精神状态(1差 2一般 3好)
     */
    private Integer mentalState;

    /**
     * 食欲(1差 2一般 3好)
     */
    private Integer appetite;

    /**
     * 排泄情况
     */
    private String excretion;

    /**
     * 备注
     */
    private String note;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
