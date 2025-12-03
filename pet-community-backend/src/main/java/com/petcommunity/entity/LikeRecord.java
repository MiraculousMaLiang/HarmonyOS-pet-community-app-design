package com.petcommunity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 点赞记录实体类
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
@Data
@TableName("like_record")
public class LikeRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 点赞ID
     */
    @TableId(type = IdType.AUTO)
    private Long likeId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 目标ID(动态/评论)
     */
    private Long targetId;

    /**
     * 类型(1动态 2评论)
     */
    private Integer targetType;

    /**
     * 点赞时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
