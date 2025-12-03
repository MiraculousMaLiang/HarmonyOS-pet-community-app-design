package com.petcommunity.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcommunity.common.exception.BusinessException;
import com.petcommunity.common.result.ResultCode;
import com.petcommunity.dto.PostDTO;
import com.petcommunity.entity.*;
import com.petcommunity.mapper.*;
import com.petcommunity.service.PostService;
import com.petcommunity.vo.PostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 动态Service实现类
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final PetMapper petMapper;
    private final LikeRecordMapper likeRecordMapper;
    private final FavoriteMapper favoriteMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Post publishPost(PostDTO postDTO) {
        // 获取当前登录用户ID
        Long userId = StpUtil.getLoginIdAsLong();

        Post post = new Post();
        BeanUtils.copyProperties(postDTO, post);
        post.setUserId(userId);
        post.setStatus(1); // 正常状态
        post.setLikeCount(0);
        post.setCommentCount(0);
        post.setShareCount(0);
        post.setViewCount(0);
        post.setIsTop(0);

        postMapper.insert(post);
        return post;
    }

    @Override
    public PostVO getPostDetail(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null || post.getStatus() == 0) {
            throw new BusinessException(ResultCode.POST_NOT_EXIST);
        }

        // 增加浏览量
        increaseViewCount(postId);

        return convertToVO(post);
    }

    @Override
    public Page<PostVO> getRecommendPosts(Integer page, Integer pageSize) {
        Page<Post> postPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getStatus, 1)
                .orderByDesc(Post::getIsTop)
                .orderByDesc(Post::getCreateTime);

        Page<Post> result = postMapper.selectPage(postPage, wrapper);
        return convertToVOPage(result);
    }

    @Override
    public Page<PostVO> getFollowingPosts(Integer page, Integer pageSize) {
        // TODO: 实现关注用户动态查询
        // 这里需要联表查询user_follow表
        return getRecommendPosts(page, pageSize);
    }

    @Override
    public Page<PostVO> getPostsByTopic(String topic, Integer page, Integer pageSize) {
        Page<Post> postPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getStatus, 1)
                .eq(Post::getTopic, topic)
                .orderByDesc(Post::getCreateTime);

        Page<Post> result = postMapper.selectPage(postPage, wrapper);
        return convertToVOPage(result);
    }

    @Override
    public Page<PostVO> getPostsByUserId(Long userId, Integer page, Integer pageSize) {
        Page<Post> postPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getUserId, userId)
                .eq(Post::getStatus, 1)
                .orderByDesc(Post::getCreateTime);

        Page<Post> result = postMapper.selectPage(postPage, wrapper);
        return convertToVOPage(result);
    }

    @Override
    public Page<PostVO> searchPosts(String keyword, Integer page, Integer pageSize) {
        Page<Post> postPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getStatus, 1)
                .and(w -> w.like(Post::getTitle, keyword)
                        .or().like(Post::getContent, keyword))
                .orderByDesc(Post::getCreateTime);

        Page<Post> result = postMapper.selectPage(postPage, wrapper);
        return convertToVOPage(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePost(Long postId, PostDTO postDTO) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new BusinessException(ResultCode.POST_NOT_EXIST);
        }

        // 检查权限
        Long userId = StpUtil.getLoginIdAsLong();
        if (!post.getUserId().equals(userId)) {
            throw new BusinessException("无权限修改");
        }

        BeanUtils.copyProperties(postDTO, post);
        post.setPostId(postId);
        return postMapper.updateById(post) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePost(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new BusinessException(ResultCode.POST_NOT_EXIST);
        }

        // 检查权限
        Long userId = StpUtil.getLoginIdAsLong();
        if (!post.getUserId().equals(userId)) {
            throw new BusinessException("无权限删除");
        }

        post.setStatus(0); // 软删除
        return postMapper.updateById(post) > 0;
    }

    @Override
    public void increaseViewCount(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post != null) {
            post.setViewCount(post.getViewCount() + 1);
            postMapper.updateById(post);
        }
    }

    /**
     * 转换为VO对象
     */
    private PostVO convertToVO(Post post) {
        PostVO vo = new PostVO();
        BeanUtils.copyProperties(post, vo);

        // 查询用户信息
        User user = userMapper.selectById(post.getUserId());
        if (user != null) {
            vo.setNickname(user.getNickname());
            vo.setAvatar(user.getAvatar());
        }

        // 查询宠物信息
        if (post.getPetId() != null) {
            Pet pet = petMapper.selectById(post.getPetId());
            if (pet != null) {
                vo.setPetName(pet.getPetName());
            }
        }

        // 查询当前用户是否点赞
        if (StpUtil.isLogin()) {
            Long userId = StpUtil.getLoginIdAsLong();
            LambdaQueryWrapper<LikeRecord> likeWrapper = new LambdaQueryWrapper<>();
            likeWrapper.eq(LikeRecord::getUserId, userId)
                    .eq(LikeRecord::getTargetId, post.getPostId())
                    .eq(LikeRecord::getTargetType, 1);
            vo.setIsLiked(likeRecordMapper.selectCount(likeWrapper) > 0);

            // 查询当前用户是否收藏
            LambdaQueryWrapper<Favorite> favoriteWrapper = new LambdaQueryWrapper<>();
            favoriteWrapper.eq(Favorite::getUserId, userId)
                    .eq(Favorite::getPostId, post.getPostId());
            vo.setIsFavorited(favoriteMapper.selectCount(favoriteWrapper) > 0);
        } else {
            vo.setIsLiked(false);
            vo.setIsFavorited(false);
        }

        return vo;
    }

    /**
     * 转换为VO分页对象
     */
    private Page<PostVO> convertToVOPage(Page<Post> postPage) {
        Page<PostVO> voPage = new Page<>(postPage.getCurrent(), postPage.getSize(), postPage.getTotal());
        voPage.setRecords(postPage.getRecords().stream()
                .map(this::convertToVO)
                .toList());
        return voPage;
    }

}
