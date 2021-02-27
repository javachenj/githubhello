package com.nancal.excel.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExportExcel {
	
	public void exportExcelFile(List<String[]> resources, OutputStream out)
			throws IOException{
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheet = workbook.createSheet("sheet1");
		
		// 列宽
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 8000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 5000);
		
		// 表头
		HSSFRow headerRow = sheet.createRow(0);
		// 表头样式
		HSSFCellStyle headerStyle = workbook.createCellStyle();
//		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 表头字体
		HSSFFont headerFont = workbook.createFont();
		headerFont.setColor(HSSFColor.VIOLET.index);
		headerFont.setFontName("宋体");
		
		headerStyle.setFont(headerFont);
		
		HSSFCell cell = null;
		cell = headerRow.createCell(0);
		cell.setCellValue("序号");
		cell.setCellStyle(headerStyle);

		cell = headerRow.createCell(1);
		cell.setCellValue("文本");
		cell.setCellStyle(headerStyle);

		cell = headerRow.createCell(2);
		cell.setCellValue("地址");
		cell.setCellStyle(headerStyle);

		cell = headerRow.createCell(3);
		cell.setCellValue("描述");
		cell.setCellStyle(headerStyle);

		cell = headerRow.createCell(4);
		cell.setCellValue("是否父目录");
		cell.setCellStyle(headerStyle);

		cell = headerRow.createCell(5);
		cell.setCellValue("是否父目录");
		cell.setCellStyle(headerStyle);
		
		// 表体样式
		HSSFCellStyle bodyStyle = workbook.createCellStyle();
		//bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 表体字体
		HSSFFont bodyFont = workbook.createFont();
		bodyFont.setColor(HSSFColor.BLACK.index);
		bodyFont.setFontName("宋体");
		
		bodyStyle.setFont(bodyFont);
		
		if(resources != null && resources.size() > 0){
			HSSFRow bodyRow = null;
			HSSFCell bodyCell = null;
			for(int i = 0; i < resources.size(); i++){
				String[] row = resources.get(i);
				bodyRow = sheet.createRow(i+1);
				
				bodyCell = bodyRow.createCell(0);
				bodyCell.setCellValue(row[0]);
				bodyCell.setCellStyle(bodyStyle);
				
				bodyCell = bodyRow.createCell(1);
				bodyCell.setCellValue(row[1]);
				bodyCell.setCellStyle(bodyStyle);
				
				bodyCell = bodyRow.createCell(2);
				bodyCell.setCellValue(row[2]);
				bodyCell.setCellStyle(bodyStyle);
				
				bodyCell = bodyRow.createCell(3);
				bodyCell.setCellValue(row[3]);
				bodyCell.setCellStyle(bodyStyle);
				
				bodyCell = bodyRow.createCell(4);
				bodyCell.setCellValue(row[4]);
				bodyCell.setCellStyle(bodyStyle);
				
				bodyCell = bodyRow.createCell(5);
				bodyCell.setCellValue(row[5]);
				bodyCell.setCellStyle(bodyStyle);
			}
		}
		
		// 输出
		workbook.write(out);
	}
	/**
	 * 文件导出方法.
	 * @param resource List<String[]> 集合类型, 要导出的具体数据集合.
	 * @param outputStream 输出流. 输出的excel文件保存的具体位置.
	 * @throws IOException
	 */
	public void exportExcel(List<String[]> resource, OutputStream outputStream)
			throws IOException{

		// 创建一个内存Excel对象.
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 创建一个表格.
		HSSFSheet sheet = workbook.createSheet("sheet1");

		// 创建表头
		// 获取表头内容.
		String[] headerStr = resource.get(0);
		HSSFRow headerRow = sheet.createRow(0);

		// 设置列宽
		for(int i = 0; i < headerStr.length; i++){
			sheet.setColumnWidth(i, 5000);
		}

		// 设置头单元格样式
		HSSFCellStyle headerStyle = workbook.createCellStyle();
//		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		// 设置字体
		HSSFFont headerFont = workbook.createFont();
		headerFont.setColor(HSSFColor.VIOLET.index);
		headerFont.setFontName("宋体");
		headerStyle.setFont(headerFont);

		// 定义表头内容.
		for(int i = 0; i < headerStr.length; i++){
			// 创建一个单元格
			HSSFCell headerCell = headerRow.createCell(i);
			headerCell.setCellStyle(headerStyle);
			headerCell.setCellValue(headerStr[i]);
		}

		// 表体内容.
		// 样式
		HSSFCellStyle bodyStyle = workbook.createCellStyle();
		//bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		// 设置字体
		HSSFFont bodyFont = workbook.createFont();
		bodyFont.setColor(HSSFColor.BLUE.index);
		bodyFont.setFontName("微软雅黑");
		bodyStyle.setFont(bodyFont);

		for(int row = 1; row < resource.size(); row++){
			// 输出的行数据
			String[] temp = resource.get(row);
			// 创建行
			HSSFRow bodyRow = sheet.createRow(row);
			// 循环创建列
			for(int cell = 0; cell < temp.length; cell++){
				HSSFCell bodyCell = bodyRow.createCell(cell);
				bodyCell.setCellStyle(bodyStyle);
				bodyCell.setCellValue(temp[cell]);
			}
		}

		// 将内存创建的excel对象,输出到文件中.
		workbook.write(outputStream);

	}
}









