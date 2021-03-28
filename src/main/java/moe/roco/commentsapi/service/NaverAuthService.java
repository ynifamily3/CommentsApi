package moe.roco.commentsapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import moe.roco.commentsapi.api.NaverApiClient;
import moe.roco.commentsapi.dto.NaverAuthProfileResponseDto;

@Service
@RequiredArgsConstructor
public class NaverAuthService {
    private final NaverApiClient naverApiClient;

    @Transactional(readOnly = true)
    public NaverAuthProfileResponseDto getUserProfile(String authorization) {
        return naverApiClient.requestProfile(authorization);
    }
}
