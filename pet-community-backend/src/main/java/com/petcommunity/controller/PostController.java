package com.petcommunity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcommunity.common.result.Result;
import com.petcommunity.dto.PostDTO;
import com.petcommunity.entity.Post;
import com.petcommunity.service.FavoriteService;
import com.petcommunity.service.LikeService;
import com.petcommunity.service.PostService;
import com.petcommunity.vo.PostVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 动态Controller
 *
 * @author Pet Community Team
 * @since 2025-12-03
 */
@Tag(name = "社区动态管理", description = "动态发布、查询、点赞、收藏等接口")
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final LikeService likeService;
    private final FavoriteService favoriteService;

    @Operation(summary = "发布动态")
    @PostMapping
    public Result<Post> publishPost(@Valid @RequestBody PostDTO postDTO) {
        Post post = postService.publishPost(postDTO);
        return Result.success(post);
    }

    @Operation(summary = "获取动态详情")
    @GetMapping("/{postId}")
    public Result<PostVO> getPostDetail(@PathVariable Long postId) {
        PostVO postVO = postService.getPostDetail(postId);
        return Result.success(postVO);
    }

    @Operation(summary = "获取推荐动态列表")
    @GetMapping("/recommend")
    public Result<Page<PostVO>> getRecommendPosts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PostVO> posts = postService.getRecommendPosts(page, pageSize);
        return Result.success(posts);
    }

    @Operation(summary = "获取关注用户动态列表")
    @GetMapping("/following")
    public Result<Page<PostVO>> getFollowingPosts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PostVO> posts = postService.getFollowingPosts(page, pageSize);
        return Result.success(posts);
    }

    @Operation(summary = "根据话题获取动态列表")
    @GetMapping("/topic/{topic}")
    public Result<Page<PostVO>> getPostsByTopic(
            @PathVariable String topic,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PostVO> posts = postService.getPostsByTopic(topic, page, pageSize);
        return Result.success(posts);
    }

    @Operation(summary = "根据用户ID获取动态列表")
    @GetMapping("/user/{userId}")
    public Result<Page<PostVO>> getPostsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PostVO> posts = postService.getPostsByUserId(userId, page, pageSize);
        return Result.success(posts);
    }

    @Operation(summary = "搜索动态")
    @GetMapping("/search")
    public Result<Page<PostVO>> searchPosts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PostVO> posts = postService.searchPosts(keyword, page, pageSize);
        return Result.success(posts);
    }

    @Operation(summary = "更新动态")
    @PutMapping("/{postId}")
    public Result<String> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostDTO postDTO) {
        boolean success = postService.updatePost(postId, postDTO);
        return success ? Result.success("更新成功") : Result.error("更新失败");
    }

    @Operation(summary = "删除动态")
    @DeleteMapping("/{postId}")
    public Result<String> deletePost(@PathVariable Long postId) {
        boolean success = postService.deletePost(postId);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Operation(summary = "点赞/取消点赞动态")
    @PostMapping("/{postId}/like")
    public Result<String> toggleLike(@PathVariable Long postId) {
        boolean isLiked = likeService.toggleLike(postId, 1); // 1表示动态
        return Result.success(isLiked ? "点赞成功" : "取消点赞成功");
    }

    @Operation(summary = "收藏/取消收藏动态")
    @PostMapping("/{postId}/favorite")
    public Result<String> toggleFavorite(@PathVariable Long postId) {
        boolean isFavorited = favoriteService.toggleFavorite(postId);
        return Result.success(isFavorited ? "收藏成功" : "取消收藏成功");
    }

    @Operation(summary = "获取我的收藏列表")
    @GetMapping("/favorites")
    public Result<Page<PostVO>> getMyFavorites(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PostVO> posts = favoriteService.getMyFavorites(page, pageSize);
        return Result.success(posts);
    }

}
