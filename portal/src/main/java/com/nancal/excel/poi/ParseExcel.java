package com.nancal.excel.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ParseExcel {
	
	public List<String[]> parseExcel(InputStream is, String suffix, int startRow) 
			throws IOException{
		
		Workbook workbook = null;
		if("xls".equals(suffix)){
			workbook = new HSSFWorkbook(is);
		} else if ("xlsx".equals(suffix)){
			workbook = new XSSFWorkbook(is);
		} else { 
			return null;
		}
		
		Sheet sheet = workbook.getSheetAt(0);
		
		if(sheet == null){
			return null;
		}
		
		int lastRowNum = sheet.getLastRowNum();
		List<String[]> list = new ArrayList<String[]>();
		
		for(int rowNum = startRow; rowNum <= lastRowNum; rowNum++){
			Row row = sheet.getRow(rowNum);
			if(row != null){
				short firstCellNum = row.getFirstCellNum();
				short lastCellNum = row.getLastCellNum();
				if(firstCellNum != lastCellNum){
					String[] rowArray = new String[lastCellNum];
					for(int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++){
						Cell cell = row.getCell(cellNum);
						if(cell == null){
							rowArray[cellNum] = "";
						}else{
							rowArray[cellNum] = parseCell(cell);
						}
					}
					
					list.add(rowArray);
				}
			}
		}
		
		return list;
	}
	
	private String parseCell(Cell cell){
		String cellStr = "";
		switch(cell.getCellType()){
		
			case HSSFCell.CELL_TYPE_NUMERIC : // 数学
				if(HSSFDateUtil.isCellDateFormatted(cell)){ // 日期,时间
					SimpleDateFormat sdf = null;
					if(cell.getCellStyle().getDataFormat() == 
							HSSFDataFormat.getBuiltinFormat("h:mm")){ // 时间 HH:mm
						sdf = new SimpleDateFormat("HH:mm");
					} else {
						sdf = new SimpleDateFormat("yyyy-MM-dd");
					}
					cellStr = sdf.format(cell.getDateCellValue());
				}else if(cell.getCellStyle().getDataFormat() == 58){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					double temp = cell.getNumericCellValue();
					Date date = DateUtil.getJavaDate(temp);
					cellStr = sdf.format(date);
				}else{
					double temp = cell.getNumericCellValue();
					String style = cell.getCellStyle().getDataFormatString();
					DecimalFormat format = new DecimalFormat();
					if(style.equals("General")){
						format.applyPattern("#");
					}
					cellStr = format.format(temp);
				}
				break;
			case HSSFCell.CELL_TYPE_STRING : // 字符串
				cellStr = cell.getRichStringCellValue().toString();
				break;
			case HSSFCell.CELL_TYPE_BLANK : // 布尔
				cellStr = "";
				break;
			default :
				cellStr = "";
		}
		
		return cellStr;
	}

}








