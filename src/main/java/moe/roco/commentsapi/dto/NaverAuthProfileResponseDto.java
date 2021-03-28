package moe.roco.commentsapi.dto;

import lombok.Data;

@Data
public class NaverAuthProfileResponseDto {
    String resultcode;
    String message;
    Response response;

    @Data
    public static class Response {
        String id;
        String email;
        String nickname;
        String profile_image;
    }
}

