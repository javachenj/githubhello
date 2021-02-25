package com.nancal.minio.web.service.impl;

import com.nancal.common.enums.CodeEnum;
import com.nancal.common.exception.ServiceException;
import com.nancal.common.utils.StringUtils;
import com.nancal.entity.uc.File;
import com.nancal.entity.uc.QFile;
import com.nancal.minio.base.BaseService;
import com.nancal.minio.base.MvcResult;
import com.nancal.minio.utils.CopyUtils;
import com.nancal.minio.web.dao.FileDao;
import com.nancal.minio.web.service.FileService;
import com.nancal.model.uc.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class FileServiceImpl extends BaseService implements FileService {

    @Autowired
    private FileDao fileDao;

    private QFile entity = QFile.file;

    //保存文件
    @Override
    @Transactional
    public MvcResult<FileModel> save(FileModel files) {
        return apply(item -> {
            File file = new File();
            CopyUtils.objectToObject(files, file);
            fileDao.save(file);
            return files;
        }, files);
    }

    //通过id查找文件
    @Override
    public MvcResult<FileModel> findById(String uuid) {
        return apply(item -> {
            File file = fileDao.findOne(entity.uuid.eq(uuid)
                    .and(entity.delFlag.eq(false)))
                    .orElseThrow(() -> new ServiceException(CodeEnum.ERROR.code(), "文件不存在"));
            FileModel fileModel = new FileModel();
            CopyUtils.objectToObject(file, fileModel);
            return fileModel;
        }, uuid);
    }

    @Override
    public List<FileModel> find(Boolean fileStatus) {

        List<File> files = fileDao.findByFileStatus(fileStatus);
        List<FileModel> fileModelList = CopyUtils.copyList(files, FileModel.class);
        fileDao.deleteAll(files);
        return fileModelList;
    }

    @Override
    @Transactional
    public void delete(FileModel fileModel) {
        File file = fileDao.findOne(entity.uuid.eq(fileModel.getUuid())
                .and(entity.delFlag.eq(false)))
                .orElseThrow(() -> new ServiceException(CodeEnum.ERROR.code(), "文件不存在"));
        fileDao.delete(file);
    }

    @Override
    public List<FileModel> findByAllId(List<Long> ids) {

        List<File> files = fileDao.findAllById(ids);
        if (StringUtils.isEmpty(files)) {
            System.out.println("文件不存在");
        }
        List<FileModel> fileModelList = CopyUtils.copyList(files, FileModel.class);
        return fileModelList;
    }

    @Transactional
    @Override
    public void deleteBatch(List<Long> id) {
        List<File> files = fileDao.findAllById(id);
        fileDao.deleteInBatch(files);
    }

}

