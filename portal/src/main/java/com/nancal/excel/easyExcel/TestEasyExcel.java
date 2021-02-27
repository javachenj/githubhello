package com.nancal.excel.easyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestEasyExcel {
    @Test
    public void writeExcel() {
        //实现excel写操作
        //1设置写入地址和文件名
        String fileName = "D:\\nancal\\test\\任务名称.xlsx";
        //设置头字体大小
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置内容字体大小
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 12);
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        //2实现写操作
        //write方法有两个参数：1文件路径名称；2实体类
        EasyExcel.write(fileName, DemoDataVo.class).sheet("任务列表").registerWriteHandler(horizontalCellStyleStrategy).doWrite(data());

    }

    @Test
    public void readExcel() {
        //实现excel读操作
        //1设置写入地址和文件名
        String fileName = "D:\\nancal\\test\\任务名称.xlsx";
        //2读取数据
        EasyExcel.read(fileName, DemoDataVo.class, new ExcelListener()).sheet().doRead();
    }


    //循环设置要添加的数据，最终封装到list集合中
    private static List<DemoDataVo> data() {
        List<DemoDataVo> list = new ArrayList<DemoDataVo>();

        DemoDataVo data = new DemoDataVo();
        data.setOrderNo("20210123");
        data.setSortie("2");
        data.setXbomNumber("XBOM");
        data.setProductName("产品名称");
        data.setPlanQty(111);
        data.setBatch("批次");
        data.setStartDate(new Date());
        data.setEndDate(new Date());
        data.setCustomer("客户名称");
        data.setProduct("产品图号");
        list.add(data);

        return list;
    }

}
