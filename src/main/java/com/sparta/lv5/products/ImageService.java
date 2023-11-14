package com.sparta.lv5.products;

import com.sparta.lv5.products.dto.ImageResponseDto;
import com.sparta.lv5.products.entity.Image;
import com.sparta.lv5.products.entity.Product;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final S3Template s3Template;

    @Value("${aws.bucket.name}")
    private String bucket;
    @Value("${spring.cloud.aws.s3.region}")
    private String region;

    public ImageResponseDto addImage(Integer productId, MultipartFile file) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new NullPointerException("해당 상품이 존재하지 않습니다."));

        String originalFileName = file.getOriginalFilename();
        String key = "images/" + UUID.randomUUID() + "_" + originalFileName;
        InputStream inputStream = file.getInputStream();
        ObjectMetadata metadata = ObjectMetadata.builder().contentType("text/plain").build();
        s3Template.upload(bucket, key, inputStream, metadata);

        String publicUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, key);

        Image newimage = new Image(originalFileName, publicUrl, product);
        imageRepository.save(newimage);

        return new ImageResponseDto(newimage);
    }
}
