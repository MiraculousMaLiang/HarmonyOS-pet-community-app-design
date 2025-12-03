package com.petcommunity.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.petcommunity.entity.Comment;
import com.petcommunity.entity.LikeRecord;
import com.petcommunity.entity.Post;
import com.petcommunity.mapper.CommentMapper;
import com.petcommunity.mapper.LikeRecordMapper;
import com.petcommunity.mapper.PostMapper;
import com.petcommunity.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 点赞Service实现类
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRecordMapper likeRecordMapper;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean toggleLike(Long targetId, Integer targetType) {
        Long userId = StpUtil.getLoginIdAsLong();

        // 查询是否已点赞
        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
                .eq(LikeRecord::getTargetId, targetId)
                .eq(LikeRecord::getTargetType, targetType);

        LikeRecord existingLike = likeRecordMapper.selectOne(wrapper);

        if (existingLike != null) {
            // 已点赞，取消点赞
            likeRecordMapper.deleteById(existingLike.getLikeId());
            decreaseLikeCount(targetId, targetType);
            return false;
        } else {
            // 未点赞，添加点赞
            LikeRecord likeRecord = new LikeRecord();
            likeRecord.setUserId(userId);
            likeRecord.setTargetId(targetId);
            likeRecord.setTargetType(targetType);
            likeRecordMapper.insert(likeRecord);
            increaseLikeCount(targetId, targetType);
            return true;
        }
    }

    /**
     * 增加点赞数
     */
    private void increaseLikeCount(Long targetId, Integer targetType) {
        if (targetType == 1) {
            // 动态
            Post post = postMapper.selectById(targetId);
            if (post != null) {
                post.setLikeCount(post.getLikeCount() + 1);
                postMapper.updateById(post);
            }
        } else if (targetType == 2) {
            // 评论
            Comment comment = commentMapper.selectById(targetId);
            if (comment != null) {
                comment.setLikeCount(comment.getLikeCount() + 1);
                commentMapper.updateById(comment);
            }
        }
    }

    /**
     * 减少点赞数
     */
    private void decreaseLikeCount(Long targetId, Integer targetType) {
        if (targetType == 1) {
            // 动态
            Post post = postMapper.selectById(targetId);
            if (post != null && post.getLikeCount() > 0) {
                post.setLikeCount(post.getLikeCount() - 1);
                postMapper.updateById(post);
            }
        } else if (targetType == 2) {
            // 评论
            Comment comment = commentMapper.selectById(targetId);
            if (comment != null && comment.getLikeCount() > 0) {
                comment.setLikeCount(comment.getLikeCount() - 1);
                commentMapper.updateById(comment);
            }
        }
    }

}
