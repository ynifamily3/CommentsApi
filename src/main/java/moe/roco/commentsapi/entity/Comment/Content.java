package moe.roco.commentsapi.entity.Comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Content {
	String textData;
	String imageData; // 첨부한 이미지 URL
}
