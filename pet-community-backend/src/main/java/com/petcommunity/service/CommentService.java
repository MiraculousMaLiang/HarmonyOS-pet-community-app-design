package com.petcommunity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcommunity.dto.CommentDTO;
import com.petcommunity.entity.Comment;
import com.petcommunity.vo.CommentVO;

import java.util.List;

/**
 * 评论Service接口
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
public interface CommentService {

    /**
     * 发布评论
     *
     * @param commentDTO 评论信息
     * @return 评论
     */
    Comment addComment(CommentDTO commentDTO);

    /**
     * 获取动态评论列表（分页）
     *
     * @param postId   动态ID
     * @param page     页码
     * @param pageSize 每页数量
     * @return 评论列表
     */
    Page<CommentVO> getCommentsByPostId(Long postId, Integer page, Integer pageSize);

    /**
     * 获取评论详情（包含子评论）
     *
     * @param commentId 评论ID
     * @return 评论VO
     */
    CommentVO getCommentDetail(Long commentId);

    /**
     * 删除评论
     *
     * @param commentId 评论ID
     * @return 是否成功
     */
    boolean deleteComment(Long commentId);

}
