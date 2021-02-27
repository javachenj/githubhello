package com.nancal.minio.web.service;

import com.nancal.minio.base.MvcResult;
import com.nancal.model.uc.FileModel;

import java.util.List;

public interface FileService {
    MvcResult<FileModel> save(FileModel fileModel);

    MvcResult<FileModel> findById(String uuid);

    List<FileModel> find(Boolean fileStatus);

    void delete(FileModel fileModel);

   List<FileModel> findByAllId(List<Long> ids);

    void deleteBatch(List<Long> id);


}
