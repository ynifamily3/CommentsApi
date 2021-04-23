package moe.roco.commentsapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moe.roco.commentsapi.dao.CommentDao;
import moe.roco.commentsapi.entity.ApiStatus.ApiStatusWithCount;
import moe.roco.commentsapi.entity.Comment.Comment;
import moe.roco.commentsapi.enums.STATUS;
import moe.roco.commentsapi.vo.CommentVo;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;

    @Override
    public ApiStatusWithCount<List<Comment>> getCommentList(String consumerID, String sequenceID, long skip, int limit) {
        ApiStatusWithCount<List<Comment>> apiStatusWithCount = new ApiStatusWithCount<>();
        apiStatusWithCount.setCount(commentDao.accessCommentCount(consumerID, sequenceID));
        var re = commentDao.accessComment(consumerID, sequenceID, skip, limit);
        apiStatusWithCount.setStatus(STATUS.SUCCESS);
        apiStatusWithCount.setResult(re);
        return apiStatusWithCount;
    }

    @Override
    public ApiStatusWithCount<List<Comment>> postComment(String consumerID, String sequenceID, CommentVo commentVo, long skip, int limit) {
        ApiStatusWithCount<List<Comment>> apiStatusWithCount = new ApiStatusWithCount<>();
        commentDao.addComment(consumerID, sequenceID, commentVo);
        apiStatusWithCount.setCount(commentDao.accessCommentCount(consumerID, sequenceID));
        apiStatusWithCount.setStatus(STATUS.SUCCESS);
        apiStatusWithCount.setResult(commentDao.accessComment(consumerID, sequenceID, skip, limit));
        return apiStatusWithCount;
    }

    @Override
    public boolean deleteComment(String id, String authType, String authorization) {
        // TODO: 권한 체크 로직
        commentDao.deleteComment(id);
        return true;
    }
}
