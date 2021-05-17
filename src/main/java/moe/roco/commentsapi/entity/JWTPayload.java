package moe.roco.commentsapi.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JWTPayload {
    String id;
    String displayName;
    String photo;
    long expires;
}
