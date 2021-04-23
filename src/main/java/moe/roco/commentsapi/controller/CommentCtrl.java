package moe.roco.commentsapi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moe.roco.commentsapi.entity.ApiStatus.ApiStatus;
import moe.roco.commentsapi.entity.ApiStatus.ApiStatusWithCount;
import moe.roco.commentsapi.entity.Comment.Comment;
import moe.roco.commentsapi.enums.STATUS;
import moe.roco.commentsapi.service.CheckAuth;
import moe.roco.commentsapi.service.CommentService;
import moe.roco.commentsapi.vo.CommentVo;

@Slf4j
@Controller
@RequestMapping(value = "/comment")
@RequiredArgsConstructor
public class CommentCtrl {

    private final CheckAuth checkAuth;
    private final CommentService commentService;

    @ResponseBody
    @ApiOperation(value = "댓글 리스트 보기 (consumer + sequence)")
    @GetMapping(value = "/{consumerID}/{sequenceID}")
    public ApiStatusWithCount<List<Comment>> getComments(@PathVariable(value = "consumerID") String consumerID,
                                                         @PathVariable(value = "sequenceID") String sequenceID,
                                                         @RequestParam(value = "skip", required = false, defaultValue = "0") long skip,
                                                         @RequestParam(value = "limit", required = false, defaultValue = "50") int limit
    ) {
        return commentService.getCommentList(consumerID, sequenceID, skip, limit);
    }

    @ResponseBody
    @ApiOperation(value = "댓글 등록하기 (인증 필요)")
    @PostMapping(value = "/{consumerID}/{sequenceID}")
    public ApiStatusWithCount<List<Comment>> postComments(@PathVariable(value = "consumerID") String consumerID,
                                                          @PathVariable(value = "sequenceID") String sequenceID,
                                                          @RequestBody CommentVo commentVo,
                                                          @RequestParam(value = "skip", required = false, defaultValue = "0") long skip,
                                                          @RequestParam(value = "limit", required = false, defaultValue = "50") int limit,
                                                          @RequestParam(value = "authType", required = false) String authType,
                                                          @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        if (authType.equals("naver") && checkAuth.isLogin(authType, authorization)) {
            return commentService.postComment(consumerID, sequenceID, commentVo, skip, limit);
        } else if (checkAuth.isLogin(authType, authorization, commentVo.getWriter().getId().split(authType + "-")[1])) {
            return commentService.postComment(consumerID, sequenceID, commentVo, skip, limit);
        } else {
            var result = new ApiStatusWithCount<List<Comment>>();
            result.setResult(List.of());
            result.setStatus(STATUS.FAILURE);
            return result;
        }
    }

    @ResponseBody
    @ApiOperation(value = "댓글 삭제하기 (인증 필요)")
    @DeleteMapping(value = "/{id}")
    public ApiStatus<String> deleteComment(@PathVariable(value = "id") String id,
                                           @RequestParam(value = "authType", required = false) String authType,
                                           @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        // TODO: 권한 체크 로직을 일원화할 필요가 있다.
        // TODO: authType enum화
        boolean result = commentService.deleteComment(id, authType, authorization);
        return result ? new ApiStatus<>(id) : new ApiStatus<>();
    }
}
