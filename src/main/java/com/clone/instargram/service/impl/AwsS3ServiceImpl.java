package com.clone.instargram.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.clone.instargram.exception.definition.ExceptionNaming;
import com.clone.instargram.util.FileUitls;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class AwsS3ServiceImpl {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    @Transactional
    public String uploadFileV1(MultipartFile multipartFile){
        validateFileExists(multipartFile);

        String category = "termproject-image/";
        String fileName = FileUitls.buildFileName(category, multipartFile.getOriginalFilename());
        // AWS SDK 객체
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        // 실제 S3 에 적용시키는 구간
        uploadImageToS3(multipartFile, fileName, objectMetadata);

        String filePath = amazonS3Client.getUrl(bucket, fileName).toString();

        return filePath;
    }

    private void validateFileExists(MultipartFile multipartFile)  {
        if (multipartFile.isEmpty()) {
            throw new RuntimeException(ExceptionNaming.FAILED_SAVED);
        }
    }

    private void uploadImageToS3(MultipartFile multipartFile, String fileName, ObjectMetadata objectMetadata){
        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RuntimeException(ExceptionNaming.FAILED_SAVED);
        }
    }


}
