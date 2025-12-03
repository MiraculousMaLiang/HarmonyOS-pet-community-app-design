package com.petcommunity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 发布动态DTO
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
@Data
public class PostDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关联宠物ID
     */
    private Long petId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 图片URLs(JSON数组字符串)
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

}
