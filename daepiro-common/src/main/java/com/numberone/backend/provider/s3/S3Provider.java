package com.numberone.backend.provider.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.numberone.backend.exception.badrequest.FileMissingException;
import com.numberone.backend.exception.badrequest.FileUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Provider {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.path}")
    private String defaultUrl;

    private final AmazonS3Client amazonS3Client;

    /**
     * S3 스토리지에 이미지를 저장합니다.
     *
     * @param multipartFile
     * @return ImageUrl
     */
    public String uploadImage(MultipartFile multipartFile) {
        checkInvalidUploadFile(multipartFile);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        String fileName = defaultUrl + "/" + UUID.randomUUID() + "." + multipartFile.getOriginalFilename();

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            log.error("S3 파일 업로드에 실패했습니다. {}", e.getMessage());
            throw new FileUploadException();
        }
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public String uploadFile(String originName, InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(ContentType.APPLICATION_JSON.toString());

        amazonS3Client.putObject(new PutObjectRequest(bucket, originName, inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, originName).toString();
    }

    private void checkInvalidUploadFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty() || multipartFile.getSize() == 0) {
            throw new FileMissingException();
        }
    }

    public String getS3FileUrl(String fileName) {
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60; /* 해당 링크는 1 시간 동안만 유효 합니다. */

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(new java.util.Date(expTimeMillis));

        return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }
}
