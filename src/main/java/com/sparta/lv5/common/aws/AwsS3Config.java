package com.sparta.lv5.common.aws;

import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

//@Configuration
public class AwsS3Config implements AwsCredentialsProvider {

    @Value("${cloud.aws.credentials.aws_access_key_id}")
    private String accessKey;

    @Value("${cloud.aws.credentials.aws_secret_access_key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Override
    public AwsCredentials resolveCredentials() {
        return null;
    }
}
