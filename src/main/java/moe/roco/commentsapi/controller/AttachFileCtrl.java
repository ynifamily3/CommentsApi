package moe.roco.commentsapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moe.roco.commentsapi.entity.ApiStatus.ApiStatus;
import moe.roco.commentsapi.enums.STATUS;
import moe.roco.commentsapi.service.S3FileUploadService;

@Slf4j
@Controller
@RequestMapping(value = "/file")
@RequiredArgsConstructor
public class AttachFileCtrl {

    private final S3FileUploadService s3FileUploadService;

    @ResponseBody
    @ApiOperation(value = "파일 첨부하기")
    @PostMapping(value = "")
    public ApiStatus<String> attachFile(@RequestPart(value = "file", required = true) final MultipartFile multipartFile) {
        ApiStatus<String> apiStatus = new ApiStatus<>();
        try {
            apiStatus.setStatus(STATUS.SUCCESS);
            log.info("파일 첨부시도 중...");
            apiStatus.setResult(s3FileUploadService.upload(multipartFile));
        } catch (Exception e) {
            apiStatus.setStatus(STATUS.FAILURE);
            apiStatus.setResult(e.toString());
        }
        return apiStatus;
    }
}
