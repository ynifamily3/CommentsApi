package moe.roco.commentsapi.api;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import moe.roco.commentsapi.dto.NaverAuthProfileResponseDto;


@Service
@RequiredArgsConstructor
public class NaverApiClient {

    private final RestTemplate restTemplate;

    public NaverAuthProfileResponseDto requestProfile(String authorization) {
        String getProfileUrl = "https://openapi.naver.com/v1/nid/getUserProfile.json?response_type=json";
        String realUrl = getProfileUrl + "&access_token=" + authorization;
        return restTemplate.getForObject(realUrl, NaverAuthProfileResponseDto.class);
    }
}
