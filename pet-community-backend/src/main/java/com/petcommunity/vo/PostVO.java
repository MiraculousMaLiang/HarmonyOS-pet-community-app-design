package com.petcommunity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 动态VO
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
@Data
public class PostVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 动态ID
     */
    private Long postId;

    /**
     * 发布用户ID
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 关联宠物ID
     */
    private Long petId;

    /**
     * 宠物名称
     */
    private String petName;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片URLs(JSON)
     */
    private String images;

    /**
     * 视频URL
     */
    private String video;

    /**
     * 话题标签
     */
    private String topic;

    /**
     * 位置信息
     */
    private String location;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 分享数
     */
    private Integer shareCount;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 是否置顶
     */
    private Integer isTop;

    /**
     * 是否已点赞
     */
    private Boolean isLiked;

    /**
     * 是否已收藏
     */
    private Boolean isFavorited;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
