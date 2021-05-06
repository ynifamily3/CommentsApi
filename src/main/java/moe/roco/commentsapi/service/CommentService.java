package moe.roco.commentsapi.service;

import java.util.List;

import moe.roco.commentsapi.entity.ApiStatus.ApiStatusWithCount;
import moe.roco.commentsapi.entity.Comment.Comment;
import moe.roco.commentsapi.vo.CommentVo;

public interface CommentService {

    // TODO: 서비스가 Apistatus 따위의 것들을 반환하도록 하지 않는 쪽으로 수정한다.
    public ApiStatusWithCount<List<Comment>> getCommentList(String consumerID, String sequenceID, long skip, int limit);

    public ApiStatusWithCount<List<Comment>> postComment(String consumerID, String sequenceID, CommentVo commentVo, long skip, int limit);

    public boolean deleteComment(String consumerID, String sequenceID, String commentID, String authType, String userID);
}

