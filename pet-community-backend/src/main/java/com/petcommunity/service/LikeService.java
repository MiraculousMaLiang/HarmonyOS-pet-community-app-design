package com.petcommunity.service;

/**
 * 点赞Service接口
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
public interface LikeService {

    /**
     * 点赞/取消点赞
     *
     * @param targetId   目标ID(动态/评论)
     * @param targetType 类型(1动态 2评论)
     * @return 是否点赞(true:点赞 false:取消点赞)
     */
    boolean toggleLike(Long targetId, Integer targetType);

}
