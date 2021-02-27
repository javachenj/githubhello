package com.nancal.web.uc.controller;

import com.nancal.entity.uc.DemoData;
import com.nancal.web.base.MvcResult;
import com.nancal.web.uc.service.TaskService;
import com.nancal.web.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "导入导出")
@RequestMapping("/poi")
public class TaskController {

    Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @ApiOperation("导入")
    @PostMapping("importTaskExcel")
    public MvcResult importTaskExcel(@RequestParam(value = "filename") MultipartFile file) {

        MvcResult<DemoData> result = new MvcResult<>();
        ExcelUtil<Object> excelUtil = new ExcelUtil<>();
        try {
          //  taskService.importTaskExcel(file);

            String filename = file.getOriginalFilename();
            InputStream is = new FileInputStream("D:\\nancal\\test\\" + filename);
            //Workbook workbook = new HSSFWorkbook(is);
            List<Object> excel = excelUtil.importExcel(is);
            System.out.println(excel);
//            List<String[]> xlsx = ExcelUtil.parseExcel(is, "xlsx", 1);
//            for (String[] str : xlsx) {
//                String s = Arrays.toString(str);
//            }
            result.setCode(1);
            result.setMsg("成功");
            logger.info("导入成功");
        } catch (IOException e) {
            result.setCode(0);
            result.setMsg("失败");
            logger.info("导入生产任务信息异常===" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @ApiOperation("导出")
    @PostMapping("/export")
    public MvcResult<DemoData> export(@RequestBody DemoData demoData) throws IOException {
        MvcResult<DemoData> result = new MvcResult<>();
        OutputStream out = null;
        try {
            List<DemoData> data = taskService.export(demoData);
            out = new FileOutputStream("D:\\nancal\\test\\cc.xlsx");
            ExcelUtil.exportExcelFile(data, out);
            result.setMsg("导出成功");
            logger.info("导出成功");

        } catch (Exception e) {
            result.setMsg("导出失败");
            logger.info("导出生产任务信息异常===" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return result;
    }
}