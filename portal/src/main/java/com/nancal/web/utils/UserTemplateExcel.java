package com.nancal.web.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;

public class UserTemplateExcel {

    public static Workbook  createTemplateToServer( String[] departList, String[] RowList) throws Exception {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("导入模板");

        // 第一行
        Row row = sheet.createRow(0);
        CellStyle style = CatalogExcelUtil.getHeadStyle(wb);

        CatalogExcelUtil.initCell(row.createCell(0), style, "用户名称(必填)");
        CatalogExcelUtil.initCell(row.createCell(1), style, "用户昵称(必填)");
        CatalogExcelUtil.initCell(row.createCell(2), style, "手机号");
        CatalogExcelUtil.initCell(row.createCell(3), style, "邮箱");
        CatalogExcelUtil.initCell(row.createCell(4), style, "用户性别");
        CatalogExcelUtil.initCell(row.createCell(5), style, "用户密码(必填)");
        // 设置部门
        departList = new String[] { "男", "女", "其他" };
        // 第3列的第1行到第21行单元格部门下拉 ，可替换为从数据库的部门表数据，
        // hidden_depart 为隐藏的sheet的别名，1为这个sheet的索引 ，考虑到有多个列绑定下拉列表
        wb = dropDownList2003(wb, sheet, departList, 1, 20, 4, 4, "hidden_depart", 1);

        // 设置层级
      // RowList = new String[] { "a", "b", "c" };
       // wb = dropDownList2003(wb, sheet, RowList, 1, 20, 3, 3, "hidden_level", 1);
       /* FileOutputStream stream = new FileOutputStream("d:\\success2.xls");
        wb.write(stream);
        stream.close();*/
       return wb;
    }

    /**
     * @param wb HSSFWorkbook对象
     * @param realSheet 需要操作的sheet对象
     * @param datas 下拉的列表数据
     * @param startRow 开始行
     * @param endRow 结束行
     * @param startCol 开始列
     * @param endCol 结束列
     * @param hiddenSheetName 隐藏的sheet名
     * @param hiddenSheetIndex 隐藏的sheet索引
     * @return
     * @throws Exception
     */
    public static HSSFWorkbook dropDownList2003(Workbook wb, Sheet realSheet, String[] datas, int startRow, int endRow,
                                                int startCol, int endCol, String hiddenSheetName, int hiddenSheetIndex)
                                                                                                                       throws Exception {

        HSSFWorkbook workbook = (HSSFWorkbook) wb;
        // 创建一个数据源sheet
        HSSFSheet hidden = workbook.createSheet(hiddenSheetName);
        // 数据源sheet页不显示
        workbook.setSheetHidden(hiddenSheetIndex, true);
        // 将下拉列表的数据放在数据源sheet上
        HSSFRow row = null;
        HSSFCell cell = null;
        for (int i = 0, length = datas.length; i < length; i++) {
            row = hidden.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(datas[i]);
        }
        // 指定下拉数据时，给定目标数据范围 hiddenSheetName!$A$1:$A5   隐藏sheet的A1到A5格的数据
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(hiddenSheetName + "!$A$1:$A" + datas.length);
        CellRangeAddressList addressList = null;
        HSSFDataValidation validation = null;
        row = null;
        cell = null;
        // 单元格样式
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
        // 循环指定单元格下拉数据
        for (int i = startRow; i <= endRow; i++) {
            row = (HSSFRow) realSheet.createRow(i);
            cell = row.createCell(startCol);
            cell.setCellStyle(style);
            addressList = new CellRangeAddressList(i, i, startCol, endCol);
            validation = new HSSFDataValidation(addressList, constraint);
            realSheet.addValidationData(validation);
        }

        return workbook;
    }
}
