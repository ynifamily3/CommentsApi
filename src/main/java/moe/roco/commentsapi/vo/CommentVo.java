package moe.roco.commentsapi.vo;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import moe.roco.commentsapi.entity.Comment.Comment;
import moe.roco.commentsapi.entity.Comment.Content;
import moe.roco.commentsapi.entity.Comment.Writer;

@Getter
@Setter
@Builder
public class CommentVo {
    Date date;
    Writer writer;
    Content content;

    @Override
    public String toString() {
        return date.toString() + " / " + writer.getNickname() + "(" + writer.getId() + "): " + content.getTextData() + " [" + content.getImageData() + "]";
    }

    public Comment toEntity(String consumerID, String sequenceID) {
        return Comment.builder()
                .recommend(0)
                .notRecommend(0)
                .cntReply(0)
                .consumerID(consumerID)
                .sequenceID(sequenceID)
                .date(date)
                .writer(writer)
                .content(content)
                .build();
    }
}
