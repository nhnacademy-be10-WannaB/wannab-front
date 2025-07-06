package shop.wannab.frontservice.global.minio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "minio")
@Getter
@Setter
public class MinioProperties {
    private String url;
    private String accessKey;
    private String secretKey;
    private String bookImageBucket;
    private String reviewImageBucket;
<<<<<<< feature/issue-90/category-search
=======
    private String publicUrl;
>>>>>>> develop

    public String getBucketName(BucketType type) {
        return switch (type) {
            case BOOK -> bookImageBucket;
            case REVIEW -> reviewImageBucket;
        };
    }
}
