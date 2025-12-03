package com.petcommunity.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcommunity.entity.Favorite;
import com.petcommunity.entity.Post;
import com.petcommunity.mapper.FavoriteMapper;
import com.petcommunity.mapper.PostMapper;
import com.petcommunity.service.FavoriteService;
import com.petcommunity.service.PostService;
import com.petcommunity.vo.PostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 收藏Service实现类
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;
    private final PostMapper postMapper;
    private final PostService postService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean toggleFavorite(Long postId) {
        Long userId = StpUtil.getLoginIdAsLong();

        // 查询是否已收藏
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId)
                .eq(Favorite::getPostId, postId);

        Favorite existingFavorite = favoriteMapper.selectOne(wrapper);

        if (existingFavorite != null) {
            // 已收藏，取消收藏
            favoriteMapper.deleteById(existingFavorite.getFavoriteId());
            return false;
        } else {
            // 未收藏，添加收藏
            Favorite favorite = new Favorite();
            favorite.setUserId(userId);
            favorite.setPostId(postId);
            favoriteMapper.insert(favorite);
            return true;
        }
    }

    @Override
    public Page<PostVO> getMyFavorites(Integer page, Integer pageSize) {
        Long userId = StpUtil.getLoginIdAsLong();

        // 查询收藏记录
        Page<Favorite> favoritePage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId)
                .orderByDesc(Favorite::getCreateTime);

        Page<Favorite> result = favoriteMapper.selectPage(favoritePage, wrapper);

        // 查询动态详情
        Page<PostVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<PostVO> voList = result.getRecords().stream()
                .map(favorite -> postService.getPostDetail(favorite.getPostId()))
                .collect(Collectors.toList());
        voPage.setRecords(voList);

        return voPage;
    }

}
