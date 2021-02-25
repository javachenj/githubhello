package com.nancal.web.uc.service;


import com.nancal.entity.uc.DemoData;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TaskService {
   int importTaskExcel(MultipartFile file) throws Exception;

   List<DemoData> export(DemoData demoData) throws IOException, InvalidFormatException;

}
