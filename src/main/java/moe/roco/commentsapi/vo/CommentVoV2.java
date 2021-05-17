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
public class CommentVoV2 {
    String authMethod;
    String textData;
    String imageData;

    @Override
    public String toString() {
        return "인증 방식: " + authMethod + "\n댓글 내용: " + textData + "\n첨부 파일" + (imageData != null ? imageData : "(없음)");
    }

    public Comment toEntity(String consumerID, String sequenceID, Writer writer) {
        writer.setNickname(authMethod + "-" + writer.getNickname()); // authMethod 접합
        return Comment.builder()
                .recommend(0)
                .notRecommend(0)
                .cntReply(0)
                .consumerID(consumerID)
                .sequenceID(sequenceID)
                .date(new Date())
                .writer(writer)
                .content(Content.builder().textData(textData).imageData(imageData).build()).build();
    }
}
