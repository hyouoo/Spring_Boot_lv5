package com.sparta.lv5.common.aws;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StreamUtils;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

//@Component
@RequiredArgsConstructor
public class MyS3Client {

    private static final String BUCKET = "mylv5bucket/images";
    private final S3Client s3Client;
    private final S3Template s3Template;

    void readFile() throws IOException {
        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(
                request -> request.bucket(BUCKET).key("a.txt")
        );
        String fileContent = StreamUtils.copyToString(response, StandardCharsets.UTF_8);
        System.out.println(fileContent);
    }

    void uploadFile(HttpServletRequest request) throws IOException {
        InputStream is = request.getInputStream();
        s3Template.upload(BUCKET, "a.txt", is, ObjectMetadata.builder().contentType("text/plain").build());

        URL signedGetUrl = s3Template.createSignedGetURL(BUCKET, "a.txt", Duration.ofMinutes(5));
    }
}
