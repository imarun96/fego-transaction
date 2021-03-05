package com.fego.transaction.common.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {

    @Bean
    public AmazonS3 getAmazonS3Client(PropertyConfig propertyConfig) {
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(propertyConfig.getAws().get("access_key_id"),
                propertyConfig.getAws().get("secret_access_key"));
        return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(propertyConfig.getAws().get("region")))
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).build();
    }
}