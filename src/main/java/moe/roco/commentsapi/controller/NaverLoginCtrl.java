package moe.roco.commentsapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moe.roco.commentsapi.dto.NaverAuthProfileResponseDto;
import moe.roco.commentsapi.service.FirebaseAuthService;
import moe.roco.commentsapi.service.NaverAuthService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/auth/naver")
public class NaverLoginCtrl {

    private final NaverAuthService naverAuthService;
    private final FirebaseAuthService firebaseAuthService;

    @ResponseBody
    @ApiOperation(value = "네이버 로그인 프로파일 보기")
    @GetMapping(value = "/profile")
    public NaverAuthProfileResponseDto getProfile(@RequestHeader("Authorization") String authorization) {
        return naverAuthService.getUserProfile(authorization);
    }
}
