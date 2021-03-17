package moe.roco.commentsapi.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moe.roco.commentsapi.entity.Comment.Comment;
import moe.roco.commentsapi.vo.CommentVo;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentDao {
    private final MongoOperations mongoOperations;

    public long accessCommentCount(String consumerID, String sequenceID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("consumerID").is(consumerID));
        query.addCriteria(Criteria.where("sequenceID").is(sequenceID));
        return mongoOperations.count(query, Comment.class);
    }

    public List<Comment> accessComment(String consumerID, String sequenceID, long skip, int limit) {
        Query query = new Query();
        query.addCriteria(Criteria.where("consumerID").is(consumerID));
        query.addCriteria(Criteria.where("sequenceID").is(sequenceID));
        query.with(Sort.by(Sort.Direction.DESC, "date"));
        return mongoOperations.find(query.skip(skip).limit(limit), Comment.class);
    }

    public void addComment(String consumerID, String sequenceID, CommentVo commentVo) {
        Comment commentEntity = commentVo.toEntity(consumerID, sequenceID);
        mongoOperations.insert(commentEntity);
    }
}
