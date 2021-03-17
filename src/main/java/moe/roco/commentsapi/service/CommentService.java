package moe.roco.commentsapi.service;

import java.util.List;

import moe.roco.commentsapi.entity.ApiStatus.ApiStatusWithCount;
import moe.roco.commentsapi.entity.Comment.Comment;
import moe.roco.commentsapi.vo.CommentVo;

public interface CommentService {

	public ApiStatusWithCount<List<Comment>> getCommentList(String consumerID, String sequenceID, long skip, int limit);

	public ApiStatusWithCount<List<Comment>> postComment(String consumerID, String sequenceID, CommentVo commentVo, long skip, int limit);
}

