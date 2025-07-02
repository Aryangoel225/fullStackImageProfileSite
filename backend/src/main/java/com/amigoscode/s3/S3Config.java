package com.amigoscode.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;

@Configuration
public class S3Config {

    // AWS S3 configuration can be added here if needed
    // For example, you can configure the S3 client, bucket name, etc.
    
    // Example:
    // @Bean
    // public S3Client s3Client() {
    //     return S3Client.builder()
    //             .region(Region.US_EAST_1)
    //             .build();
    // }
    @Value("${aws.region}")
    private String awsRegion;
    
    @Bean
    public S3Client s3Client() {
        S3Client client = S3Client.builder()
                .region(Region.of(awsRegion))
                .build();
        return client;
    }
 }

