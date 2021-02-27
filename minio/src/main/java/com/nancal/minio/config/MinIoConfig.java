package com.nancal.minio.config;

import com.nancal.minio.base.entity.MinioProp;
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