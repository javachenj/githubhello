package com.nancal.minio.web.controller;

import com.nancal.common.utils.StringUtils;
import com.nancal.minio.base.AjaxResult;
import com.nancal.minio.base.MvcResult;
import com.nancal.minio.utils.MinioUtils;
import com.nancal.minio.web.service.FileService;
import com.nancal.model.uc.FileModel;
import io.minio.MinioClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Api(tags = "MinIo管理中心")
@RestController
@RequestMapping("/minIo")
public class MinIoController {

    private static final Logger logger = LoggerFactory.getLogger(MinIoController.class);

    @Value("${minio.endpoint}")
    private String ENDPOINT;

    @Value("${minio.bucketName}")
    private String BUCKETNAME;

    @Value("${minio.accessKey}")
    private String ACCESSKEY;

    @Value("${minio.secretKey}")
    private String SECRETKEY;

    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     */
    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        FileModel files = null;
        InputStream input=null;
        if (null != file) {
            try {
                StringBuilder s = new StringBuilder();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                s.append(sdf.format(new Date()).replaceAll("-", "") + "/");
                MinioClient minioClient = new MinioClient(ENDPOINT, ACCESSKEY, SECRETKEY);
                // bucket 不存在，创建
                if (!minioClient.bucketExists(BUCKETNAME)) {
                    minioClient.makeBucket(BUCKETNAME);
                }
                //得到文件流
                input = file.getInputStream();
                //文件名
                //String fileName = uuid + "/images." + FilenameUtils.getExtension(file.getOriginalFilename());
                String filename = file.getOriginalFilename();
                String new_fileName = s.append(RandomUtils.nextInt(1, 2000) + filename).toString();
                String suffix = filename.substring(filename.lastIndexOf(".") + 1);
                //类型
                String contentType = file.getContentType();
                //把文件放置MinIo桶(文件夹)
                minioClient.putObject(BUCKETNAME, new_fileName, input, contentType);
                //StringBuilder fileUrl = new StringBuilder(ENDPOINT);
                //String url = fileUrl.append(fileName).toString();
                files = new FileModel();
                files.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
                files.setFileName(new_fileName);
                files.setCreateAt(LocalDateTime.now());
                files.setUpdateAt(LocalDateTime.now());
                files.setFileStatus(true);
                files.setFileSuffix(suffix);
                fileService.save(files);
                logger.info("上传成功");
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (input!=null){
                    input.close();
                }
            }
        }
        return files.getUuid();
    }

    @ApiOperation("根据id删除文件")
    @DeleteMapping("/deleteById/{uuid}")
    public AjaxResult deleteByUuid(@PathVariable("uuid") String uuid) {
        try {
            MvcResult<FileModel> fileModel = fileService.findById(uuid);
            if (StringUtils.isNull(fileModel)) {
                System.out.println("文件不存在");
            }
            FileModel file = fileModel.getData();
            String fileName = file.getFileName();
            minioUtils.removeObject(BUCKETNAME, fileName);
            logger.info("文件删除成功");
            try {
                fileService.delete(file);
            } catch (Exception e) {
                logger.error("文件记录删除失败: ", e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success("删除成功");
    }

    @ApiOperation("根据多个id删除文件")
    @PostMapping("/deleteByIds/{ids}")
    public AjaxResult deleteById(@RequestBody List<Long> ids) {
        List<FileModel> fileModelList = fileService.findByAllId(ids);
        try {
            try {
                for (FileModel fileModel : fileModelList) {
                    minioUtils.removeObject(BUCKETNAME, fileModel.getFileName());
                }
            } catch (Exception e) {
                logger.error("删除文件失败");
                e.printStackTrace();
            }
            fileService.deleteBatch(ids);
        } catch (Exception e) {
            logger.error("删除失败");
            e.printStackTrace();
        }
        return AjaxResult.success("删除成功");
    }

    /**
     * 下载文件到本地
     *
     * @param uuid
     * @param response
     * @return
     * @throws Exception
     */
    @ApiOperation("下载文件到本地")
    @GetMapping("/download/{uuid}")
    public void download(@PathVariable("uuid") String uuid, HttpServletResponse response) throws Exception {

        MvcResult<FileModel> result = fileService.findById(uuid);
        FileModel fileModel = result.getData();
        String fileName = fileModel.getFileName();
        OutputStream outputStream = null;
        InputStream inputStream = null;
        StringBuilder s = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        s.append(sdf.format(new Date()) + "/");
        try {
            MinioClient minioClient = new MinioClient(ENDPOINT, ACCESSKEY, SECRETKEY);
            inputStream = minioClient.getObject(BUCKETNAME, fileName);
            byte buf[] = new byte[1024];
            int length = 0;
            File file = new File(fileName);
            String name = file.getName();
            String new_filename = sdf.format(new Date()).replace("-", "") + "_" + URLDecoder.decode(name, "UTF-8");
            response.reset();
            //万能乱码问题解决
            //name = new String(name.getBytes(), "ISO-8859-1");
            //FileCopyUtils.copy(inputStream, response.getOutputStream);
            response.setHeader("Content-Disposition", "attachment;filename=" + new_filename);
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            outputStream = response.getOutputStream();
            while ((length = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
            logger.info("下载成功");
        } catch (Exception ex) {
            logger.info("下载失败：", ex.getMessage());
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 在浏览器预览图片
     *
     * @param uuid
     * @param response
     * @throws Exception
     */
    @ApiOperation("通过id预览图片")
    @GetMapping("/preViewPicture/{uuid}")
    public void preViewPicture(@PathVariable("uuid") String uuid, HttpServletResponse response) throws Exception {
        response.setContentType("image/jpeg");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            MvcResult<FileModel> fileModel = fileService.findById(uuid);
            FileModel data = fileModel.getData();
            String fileName = data.getFileName();
//            String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
//            Pattern pattern = Pattern.compile(reg);
//            Matcher matcher = pattern.matcher(fileName);
            InputStream stream = minioUtils.getObject(BUCKETNAME, fileName);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = stream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] bytes = output.toByteArray();
            out.write(bytes == null ? new byte[0] : bytes);
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    @ApiOperation("通过id修改文件状态")
    @PostMapping("/updateFileStatus/{uuid}")
    public MvcResult<FileModel> updateFileStatus(@PathVariable("uuid") String uuid) {
        FileModel fileModel = null;
        try {
            MvcResult<FileModel> result = fileService.findById(uuid);
            fileModel = result.getData();
            if (StringUtils.isNull(fileModel)) {
                System.err.println("文件不存在");
            }
            if (fileModel.getFileStatus().equals(true)) {
                fileModel.setFileStatus(false);
                logger.info("修改成功,当前状态可用");
            } else {
                fileModel.setFileStatus(true);
                logger.info("修改成功,当前状态不可用");
            }
        } catch (Exception e) {
            logger.error("修改失败:", e.getMessage());
        }
        return fileService.save(fileModel);
    }

    @ApiOperation("通过多个id修改文件状态")
    @PostMapping("/updateBatchFileStatus/{id}")
    public MvcResult<FileModel> updateBatchFileStatus(@RequestBody List<Long> id) {
        try {
            List<FileModel> fileModels = fileService.findByAllId(id);
            if (StringUtils.isNull(fileModels)) {
                System.out.println("文件不存在");
            }
            for (FileModel fileModel : fileModels) {
                if (fileModel.getFileStatus().equals(true)) {
                    fileModel.setFileStatus(false);
                    logger.info("修改成功,当前状态可用");
                } else {
                    fileModel.setFileStatus(true);
                    logger.info("修改成功,当前状态不可用");
                }
                fileService.save(fileModel);
            }
        } catch (Exception e) {
            logger.error("修改失败:", e.getMessage());
        }
        MvcResult<FileModel> result = new MvcResult<>();
        result.setCode(200);
        result.setMsg("修改成功");
        return result;
    }
}
