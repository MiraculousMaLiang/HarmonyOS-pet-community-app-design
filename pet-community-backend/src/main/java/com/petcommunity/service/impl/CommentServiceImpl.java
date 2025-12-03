package com.petcommunity.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcommunity.common.exception.BusinessException;
import com.petcommunity.dto.CommentDTO;
import com.petcommunity.entity.Comment;
import com.petcommunity.entity.LikeRecord;
import com.petcommunity.entity.Post;
import com.petcommunity.entity.User;
import com.petcommunity.mapper.CommentMapper;
import com.petcommunity.mapper.LikeRecordMapper;
import com.petcommunity.mapper.PostMapper;
import com.petcommunity.mapper.UserMapper;
import com.petcommunity.service.CommentService;
import com.petcommunity.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 评论Service实现类
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final LikeRecordMapper likeRecordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Comment addComment(CommentDTO commentDTO) {
        // 获取当前登录用户ID
        Long userId = StpUtil.getLoginIdAsLong();

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setUserId(userId);
        comment.setStatus(1);
        comment.setLikeCount(0);

        commentMapper.insert(comment);

        // 更新动态评论数
        Post post = postMapper.selectById(commentDTO.getPostId());
        if (post != null) {
            post.setCommentCount(post.getCommentCount() + 1);
            postMapper.updateById(post);
        }

        return comment;
    }

    @Override
    public Page<CommentVO> getCommentsByPostId(Long postId, Integer page, Integer pageSize) {
        Page<Comment> commentPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getPostId, postId)
                .eq(Comment::getStatus, 1)
                .isNull(Comment::getParentId) // 只查询一级评论
                .orderByDesc(Comment::getCreateTime);

        Page<Comment> result = commentMapper.selectPage(commentPage, wrapper);

        // 转换为VO并查询子评论
        Page<CommentVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<CommentVO> voList = result.getRecords().stream()
                .map(comment -> {
                    CommentVO vo = convertToVO(comment);
                    // 查询子评论
                    vo.setChildren(getChildComments(comment.getCommentId()));
                    return vo;
                })
                .toList();
        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public CommentVO getCommentDetail(Long commentId) {
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null || comment.getStatus() == 0) {
            throw new BusinessException("评论不存在");
        }

        CommentVO vo = convertToVO(comment);
        vo.setChildren(getChildComments(commentId));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteComment(Long commentId) {
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) {
            throw new BusinessException("评论不存在");
        }

        // 检查权限
        Long userId = StpUtil.getLoginIdAsLong();
        if (!comment.getUserId().equals(userId)) {
            throw new BusinessException("无权限删除");
        }

        comment.setStatus(0); // 软删除
        boolean success = commentMapper.updateById(comment) > 0;

        if (success) {
            // 更新动态评论数
            Post post = postMapper.selectById(comment.getPostId());
            if (post != null && post.getCommentCount() > 0) {
                post.setCommentCount(post.getCommentCount() - 1);
                postMapper.updateById(post);
            }
        }

        return success;
    }

    /**
     * 获取子评论列表
     */
    private List<CommentVO> getChildComments(Long parentId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getParentId, parentId)
                .eq(Comment::getStatus, 1)
                .orderByAsc(Comment::getCreateTime);

        return commentMapper.selectList(wrapper).stream()
                .map(this::convertToVO)
                .toList();
    }

    /**
     * 转换为VO对象
     */
    private CommentVO convertToVO(Comment comment) {
        CommentVO vo = new CommentVO();
        BeanUtils.copyProperties(comment, vo);

        // 查询评论用户信息
        User user = userMapper.selectById(comment.getUserId());
        if (user != null) {
            vo.setNickname(user.getNickname());
            vo.setAvatar(user.getAvatar());
        }

        // 查询回复目标用户信息
        if (comment.getReplyToUserId() != null) {
            User replyToUser = userMapper.selectById(comment.getReplyToUserId());
            if (replyToUser != null) {
                vo.setReplyToUserNickname(replyToUser.getNickname());
            }
        }

        // 查询当前用户是否点赞
        if (StpUtil.isLogin()) {
            Long userId = StpUtil.getLoginIdAsLong();
            LambdaQueryWrapper<LikeRecord> likeWrapper = new LambdaQueryWrapper<>();
            likeWrapper.eq(LikeRecord::getUserId, userId)
                    .eq(LikeRecord::getTargetId, comment.getCommentId())
                    .eq(LikeRecord::getTargetType, 2); // 2表示评论
            vo.setIsLiked(likeRecordMapper.selectCount(likeWrapper) > 0);
        } else {
            vo.setIsLiked(false);
        }

        return vo;
    }

}
