package shop.wannab.frontservice.global.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    public String uploadFile(MultipartFile file, BucketType bucketType) {
        try {
            String bucket = minioProperties.getBucketName(bucketType);
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(fileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());


            return minioProperties.getPublicUrl() + "/" + bucket + "/" + fileName;

        } catch (Exception e) {
            System.err.println("MinIO 업로드 실패: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }


    public String uploadFiles(MultipartFile[] files, BucketType bucketType) {
        if (files == null || files.length == 0) {
            return "";
        }
        List<String> urlList = new ArrayList<>();
        for(MultipartFile file:files){
            if (!file.isEmpty()) {
                String url = uploadFile(file, bucketType);
                urlList.add(url);
            }
        }
        return String.join(",", urlList);
    }
}
