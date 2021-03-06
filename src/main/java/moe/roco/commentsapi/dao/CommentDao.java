package moe.roco.commentsapi.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moe.roco.commentsapi.entity.Comment.Comment;

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

    public void addComment(String consumerID, String sequenceID, Comment comment) {
        mongoOperations.insert(comment);
    }

    public void deleteComment(String consumerID, String sequenceID, String commentID, String authType, String userID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(new ObjectId(commentID)));
        query.addCriteria(Criteria.where("consumerID").is(consumerID));
        query.addCriteria(Criteria.where("sequenceID").is(sequenceID));
        query.addCriteria(Criteria.where("writer._id").is(authType + "-" + userID));
        mongoOperations.remove(query, Comment.class);
    }
}
