package com.amigoscode.s3;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.io.IOException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
    private final S3Client s3Client;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }
    public void putObject(String bucketName, String key, byte[] file) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.putObject(objectRequest, RequestBody.fromBytes(file));
    }

    public byte[] getObject(String bucketName, String key) throws java.io.IOException {

        GetObjectRequest getObjectRequest = (GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build());
            
        ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(getObjectRequest);

        try {
            return responseInputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read object from S3", e);
        }
    }

}
