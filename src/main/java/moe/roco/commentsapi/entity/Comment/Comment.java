package moe.roco.commentsapi.entity.Comment;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Comment {
    @Id
    String id;
    String consumerID;
    String sequenceID;
    Date date;
    Writer writer;
    Content content;
    long recommend;
    long notRecommend;
    long cntReply; // 답글 수
}
