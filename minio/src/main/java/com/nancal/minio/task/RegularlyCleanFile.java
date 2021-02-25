package com.nancal.minio.task;

import com.nancal.minio.utils.MinioUtils;
import com.nancal.minio.web.service.FileService;
import com.nancal.model.uc.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 定时器
 */
@Component
@EnableScheduling
public class RegularlyCleanFile {

    @Autowired
    private FileService fileService;

    @Autowired
    private MinioUtils minioUtils;

    @Value("${minio.bucketName}")
    private String BUCKETNAME;

    //定时删除不可用文件  每天凌晨2点清除
    @Scheduled(cron = "0 0 2 * * ? ")
    public void regularlyCleanFile() throws Exception {
        List<FileModel> fileModelList = fileService.find(true);
        for (FileModel fileModel : fileModelList) {
            minioUtils.removeObject(BUCKETNAME, fileModel.getFileName());
            System.out.println("删除成功" + new Date());
        }
    }
}

