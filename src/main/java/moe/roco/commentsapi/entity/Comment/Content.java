package moe.roco.commentsapi.entity.Comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Content {
    String textData;
    String imageData; // 첨부한 이미지 URL
}
