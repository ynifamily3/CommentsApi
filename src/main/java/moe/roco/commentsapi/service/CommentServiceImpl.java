package moe.roco.commentsapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moe.roco.commentsapi.dao.CommentDao;
import moe.roco.commentsapi.entity.ApiStatus.ApiStatus;
import moe.roco.commentsapi.entity.ApiStatus.ApiStatusWithCount;
import moe.roco.commentsapi.entity.Comment.Comment;
import moe.roco.commentsapi.entity.Comment.Writer;
import moe.roco.commentsapi.enums.STATUS;
import moe.roco.commentsapi.vo.CommentVo;
import moe.roco.commentsapi.vo.CommentVoV2;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;
    private final CheckAuth checkAuth;

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
        Comment commentEntity = commentVo.toEntity(consumerID, sequenceID);
        commentDao.addComment(consumerID, sequenceID, commentEntity);
        apiStatusWithCount.setCount(commentDao.accessCommentCount(consumerID, sequenceID));
        apiStatusWithCount.setStatus(STATUS.SUCCESS);
        apiStatusWithCount.setResult(commentDao.accessComment(consumerID, sequenceID, skip, limit));
        return apiStatusWithCount;
    }

    @Override
    public ApiStatus<Boolean> postComment(String consumerID, String sequenceID, String authorization, CommentVoV2 commentVoV2) {
        ApiStatus<Boolean> apiStatus = new ApiStatus<>();
        var user = checkAuth.getLoginedUserInfo(authorization);
        if (user == null) {
            apiStatus.setMessage("로그인이 필요합니다.");
            apiStatus.setStatus(STATUS.FAILURE);
            apiStatus.setResult(false);
        } else {
            Comment commentEntity = commentVoV2.toEntity(consumerID, sequenceID, Writer.builder()
                    .id(user.getId()).nickname(user.getDisplayName()).profilePhoto(user.getPhoto()).build());
            try {
                commentDao.addComment(consumerID, sequenceID, commentEntity);
                apiStatus.setMessage("등록 성공");
                apiStatus.setStatus(STATUS.SUCCESS);
                apiStatus.setResult(true);
            } catch (Exception e) {
                apiStatus.setMessage(e.getMessage());
                apiStatus.setStatus(STATUS.FAILURE);
                apiStatus.setResult(false);
            }
        }
        return apiStatus;
    }


    @Override
    public boolean deleteComment(String consumerID, String sequenceID, String commentID, String authType, String userID) {
        try {
            commentDao.deleteComment(consumerID, sequenceID, commentID, authType, userID);
            return true;
        } catch (Exception e) {
            log.error("삭제 실패.." + commentID);
            log.error(e.getMessage());
            return false;
        }
    }
}
