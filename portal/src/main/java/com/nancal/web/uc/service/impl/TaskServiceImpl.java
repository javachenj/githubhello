package com.nancal.web.uc.service.impl;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.nancal.entity.uc.DemoData;
import com.nancal.web.uc.dao.TaskDao;
import com.nancal.web.uc.service.TaskService;
import com.nancal.web.utils.CopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskDao taskDao;

    @Override
    public int importTaskExcel(MultipartFile file) throws Exception {
        int result = 0;
        List<DemoData> demoDataList = new ArrayList<>();
        String filename = file.getOriginalFilename();
        String s = filename.substring(filename.lastIndexOf(".") + 1);
        System.out.println(s);
        Workbook workbook = null;
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            if (s.equals("xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                workbook = new HSSFWorkbook(inputStream);
            }
            Sheet sheetAt = workbook.getSheetAt(0);
            if (sheetAt != null) {
                for (int line = 1; line <= sheetAt.getLastRowNum(); line++) {
                    DemoData demoData1 = new DemoData();
                    Row row = sheetAt.getRow(line);
                    if (null == row) {
                        continue;
                    }
                    try {
                        row.getCell(0).setCellType(CellType.STRING);
                        demoData1.setOrderNo(row.getCell(0).getStringCellValue());
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("读取excel异常。。。");

                    }
                    try {
                        row.getCell(1).setCellType(CellType.STRING);

                        demoData1.setSortie(row.getCell(1).getStringCellValue());
                    } catch (Exception e) {
                        throw new RuntimeException("读取excel异常。。。");
                    }
                    //XBOM号
                    try {
                        String xbomNumber = row.getCell(2).toString();
                        demoData1.setXbomNumber(xbomNumber);
                    } catch (Exception e) {
                        throw new RuntimeException("读取excel异常。。。");
                    }
                    //产品名称
                    try {
                        String productName = row.getCell(3).toString();
                        demoData1.setProductName(productName);
                    } catch (Exception e) {
                        throw new RuntimeException("读取excel异常。。。");
                    }
                    //计划数量
                    try {
                        double planQty = row.getCell(4).getNumericCellValue();
                        demoData1.setPlanQty((int) planQty);
                    } catch (Exception e) {
                        throw new RuntimeException("读取excel异常。。。");
                    }
                    //批次
                    try {
                        String batch = row.getCell(5).toString();
                        demoData1.setBatch(batch);
                    } catch (Exception e) {
                        throw new RuntimeException("读取excel异常。。。");
                    }
                    //计划开始时间
                    SimpleDateFormat format = null;
                    try {
                        String dateCellValue = row.getCell(6).toString();
                        format = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = format.parse(dateCellValue);
                        demoData1.setStartDate(startDate);
                    } catch (ParseException e) {
                        throw new RuntimeException("读取excel异常。。。");
                    }
                    //计划结束时间
                    try {
                        String endDate = row.getCell(7).toString();
                        Date endDates = format.parse(endDate);
                        demoData1.setEndDate(endDates);
                    } catch (ParseException e) {
                        throw new RuntimeException("读取excel异常。。。");
                    }
                    //图号
                    try {
                        String product = row.getCell(8).toString();
                        demoData1.setProduct(product);
                    } catch (Exception e) {
                        throw new RuntimeException("读取excel异常。。。");
                    }
                    //客户名称
                    try {
                        if (row.getCell(9) != null) {
                            String customer = row.getCell(9).toString();
                            demoData1.setCustomer(customer);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("读取excel异常。。。");
                    }
                    demoDataList.add(demoData1);
                    List<DemoData> orderNos = taskDao.findByOrderNo(demoData1.getOrderNo());
                    if (orderNos != null && orderNos.size() > 0) {
                        throw new RuntimeException("订单重复");
                    }
                }
                for (DemoData demoData : demoDataList) {
                    List<DemoData> byOrderNo = taskDao.findByOrderNo(demoData.getOrderNo());
                    if (byOrderNo != null) {
                        demoData.setCreateByCode("CF_MES");
                        // demoData.setCreateByName(AppUserUtil.getLoginAppUser().getNickname());
                        demoData.setCreateTime(new Date());
                        taskDao.save(demoData);
                    } else {
                        DemoData demoData1 = new DemoData();
                        CopyUtils.objectToObject(demoData, demoData1);
                        //                    result = taskDao.updateDemoData(demoData,byOrderNo.get(0).getMesTaskId());
                        taskDao.save(demoData1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            workbook.close();
        }
        return result;
    }


    @Override
    public List<DemoData> export(DemoData demoData) throws IOException, InvalidFormatException {
//        //实现excel写操作
        //1设置写入地址和文件名
        String fileName = "D:\\nancal\\test\\任务名称.xlsx";
        //设置头字体大小
        WriteCellStyle headWriteCellStyle=new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont headWriteFont=new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置内容字体大小
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 12);
        WriteCellStyle contentWriteCellStyle=new WriteCellStyle();
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        //2实现写操作
//
        //write方法有两个参数：1文件路径名称；2实体类
//        EasyExcel.write(fileName, DemoData.class).sheet("任务列表").registerWriteHandler(horizontalCellStyleStrategy).doWrite(data1);
        return taskDao.findByOrderNo(demoData.getOrderNo());
    }

}
