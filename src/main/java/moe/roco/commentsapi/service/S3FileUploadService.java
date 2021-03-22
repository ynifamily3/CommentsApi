package moe.roco.commentsapi.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileUploadService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl;

    private final AmazonS3Client amazonS3Client;

    public String upload(MultipartFile uploadFile) throws IOException {
        String origName = uploadFile.getOriginalFilename();
        log.info(origName);
        String url;
        int index = origName != null ? origName.lastIndexOf('.') : -1;
        final String ext = index != -1 ? origName.substring(origName.lastIndexOf('.')) : "";
        final String saveFileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;
        File file = new File(System.getProperty("user.dir") + "/" + saveFileName);
        log.info(saveFileName);
        log.info("**" + defaultUrl);
        uploadFile.transferTo(file); // 로컬에 저장.
        uploadOnS3(saveFileName, file);
        url = defaultUrl + "/" + saveFileName;
        log.info(defaultUrl);
        if (!file.delete()) {
            log.error("임시 파일 삭제 실패..");
        }
        return url;
    }

    private void uploadOnS3(final String findName, final File file) throws AmazonServiceException {
        // AWS S3 전송 객체 생성
        final TransferManager transferManager = new TransferManager(this.amazonS3Client);
        // 요청 객체 생성
        final PutObjectRequest request = new PutObjectRequest(bucket, findName, file);
        // 업로드 시도
        final Upload upload = transferManager.upload(request);

        try {
            upload.waitForCompletion();
        } catch (AmazonClientException | InterruptedException amazonClientException) {
//            log.error(amazonClientException.getMessage());
            log.error(amazonClientException.toString());
        }
    }
}


