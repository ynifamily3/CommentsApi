package moe.roco.commentsapi.entity.Comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Writer {
    String id;
    String nickname;
    String profilePhoto;
}
