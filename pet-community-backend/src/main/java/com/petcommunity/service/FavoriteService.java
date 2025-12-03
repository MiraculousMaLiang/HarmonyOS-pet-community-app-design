package com.petcommunity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcommunity.vo.PostVO;

/**
 * 收藏Service接口
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
public interface FavoriteService {

    /**
     * 收藏/取消收藏动态
     *
     * @param postId 动态ID
     * @return 是否收藏(true:收藏 false:取消收藏)
     */
    boolean toggleFavorite(Long postId);

    /**
     * 获取我的收藏列表（分页）
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return 收藏的动态列表
     */
    Page<PostVO> getMyFavorites(Integer page, Integer pageSize);

}
