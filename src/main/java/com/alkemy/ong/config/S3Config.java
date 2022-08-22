package com.alkemy.ong.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    @Value("${s3.access:AKIAS2JWQJCDKFOJLG4T}")
    private String accessKey;

    @Value("${s3.secret:s8c2mZI/GVgeaSGOmVHDbyMmlzaC+LnWCiYTTla9}")
    private String secretKey;

    @Value("${s3.region}")
    private String region;

    @Bean
    public AmazonS3 amazonS3() {
        final AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}
