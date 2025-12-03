package com.petcommunity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcommunity.dto.PostDTO;
import com.petcommunity.entity.Post;
import com.petcommunity.vo.PostVO;

/**
 * 动态Service接口
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
public interface PostService {

    /**
     * 发布动态
     *
     * @param postDTO 动态信息
     * @return 动态
     */
    Post publishPost(PostDTO postDTO);

    /**
     * 获取动态详情
     *
     * @param postId 动态ID
     * @return 动态VO
     */
    PostVO getPostDetail(Long postId);

    /**
     * 获取推荐动态列表（分页）
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return 动态列表
     */
    Page<PostVO> getRecommendPosts(Integer page, Integer pageSize);

    /**
     * 获取关注用户动态列表（分页）
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return 动态列表
     */
    Page<PostVO> getFollowingPosts(Integer page, Integer pageSize);

    /**
     * 根据话题获取动态列表（分页）
     *
     * @param topic    话题
     * @param page     页码
     * @param pageSize 每页数量
     * @return 动态列表
     */
    Page<PostVO> getPostsByTopic(String topic, Integer page, Integer pageSize);

    /**
     * 根据用户ID获取动态列表（分页）
     *
     * @param userId   用户ID
     * @param page     页码
     * @param pageSize 每页数量
     * @return 动态列表
     */
    Page<PostVO> getPostsByUserId(Long userId, Integer page, Integer pageSize);

    /**
     * 搜索动态
     *
     * @param keyword  关键词
     * @param page     页码
     * @param pageSize 每页数量
     * @return 动态列表
     */
    Page<PostVO> searchPosts(String keyword, Integer page, Integer pageSize);

    /**
     * 更新动态
     *
     * @param postId  动态ID
     * @param postDTO 动态信息
     * @return 是否成功
     */
    boolean updatePost(Long postId, PostDTO postDTO);

    /**
     * 删除动态
     *
     * @param postId 动态ID
     * @return 是否成功
     */
    boolean deletePost(Long postId);

    /**
     * 增加浏览量
     *
     * @param postId 动态ID
     */
    void increaseViewCount(Long postId);

}
