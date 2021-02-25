package com.nancal.minio.config;

//@Component
//@Data
//public class MinIoConfig implements InitializingBean {
//    @Value("${minio.endpoint}")
//    private String endpoint;
//    @Value("${minio.bucketName}")
//    private String bucketname;
//    @Value("${minio.accessKey}")
//    private String accesskey;
//    @Value("${minio.secretKey}")
//    private String secretkey;
//
//    /**
//     * 连接地址
//     */
//    private static String ENDPOINT;
//    /**
//     * 存储桶名称
//     */
//    private static String BUCKETNAME;
//    /**
//     * 用户名
//     */
//    private static String ACCESSKEY;
//    /**
//     * 密码
//     */
//    private static String SECRETKEY;
//
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        ENDPOINT=this.endpoint;
//        BUCKETNAME=this.bucketname;
//        ACCESSKEY=this.accesskey;
//        SECRETKEY=this.secretkey;
//
//    }
//}


import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinioProp.class)
public class MinIoConfig {

    @Autowired
    private MinioProp minioProp;

    /**
     * 获取MinioClient
     */
    @Bean
    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(minioProp.getEndpoint(),minioProp.getAccesskey(),minioProp.getAccesskey());
    }

}