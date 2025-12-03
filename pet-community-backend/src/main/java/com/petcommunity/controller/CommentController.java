package com.petcommunity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcommunity.common.result.Result;
import com.petcommunity.dto.CommentDTO;
import com.petcommunity.entity.Comment;
import com.petcommunity.service.CommentService;
import com.petcommunity.service.LikeService;
import com.petcommunity.vo.CommentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 评论Controller
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
@Tag(name = "评论管理", description = "评论发布、查询、点赞等接口")
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final LikeService likeService;

    @Operation(summary = "发布评论")
    @PostMapping
    public Result<Comment> addComment(@Valid @RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.addComment(commentDTO);
        return Result.success(comment);
    }

    @Operation(summary = "获取动态评论列表")
    @GetMapping("/post/{postId}")
    public Result<Page<CommentVO>> getCommentsByPostId(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        Page<CommentVO> comments = commentService.getCommentsByPostId(postId, page, pageSize);
        return Result.success(comments);
    }

    @Operation(summary = "获取评论详情")
    @GetMapping("/{commentId}")
    public Result<CommentVO> getCommentDetail(@PathVariable Long commentId) {
        CommentVO commentVO = commentService.getCommentDetail(commentId);
        return Result.success(commentVO);
    }

    @Operation(summary = "删除评论")
    @DeleteMapping("/{commentId}")
    public Result<String> deleteComment(@PathVariable Long commentId) {
        boolean success = commentService.deleteComment(commentId);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Operation(summary = "点赞/取消点赞评论")
    @PostMapping("/{commentId}/like")
    public Result<String> toggleLike(@PathVariable Long commentId) {
        boolean isLiked = likeService.toggleLike(commentId, 2); // 2表示评论
        return Result.success(isLiked ? "点赞成功" : "取消点赞成功");
    }

}
