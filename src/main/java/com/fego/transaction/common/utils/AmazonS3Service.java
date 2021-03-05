package com.fego.transaction.common.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class AmazonS3Service {

    private static final Logger logger = LoggerFactory.getLogger(AmazonS3Service.class);
    private static final String S3_SUCCESS_MSG = "File has been saved in S3 bucket.";
    @Autowired
    private AmazonS3 amazonS3;

    public void saveFileIntoS3(String bucketName, String fileName, URL url) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        InputStream targetStream = null;
        try {
            objectMetadata.setContentLength(url.openConnection().getContentLengthLong());
            targetStream = url.openStream();
        } catch (IOException e) {
            logger.error("Exception has been occurred - {}", e.getMessage());
        }
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, targetStream, objectMetadata);
        amazonS3.putObject(putObjectRequest);
        amazonS3.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
        logger.info(S3_SUCCESS_MSG);
    }

    public void saveFileIntoS3(String bucketName, String fileName, byte[] bytes) {
        InputStream stream = new ByteArrayInputStream(bytes);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(bytes.length);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, stream, objectMetadata);
        amazonS3.putObject(putObjectRequest);
        amazonS3.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
        logger.info(S3_SUCCESS_MSG);
    }
}