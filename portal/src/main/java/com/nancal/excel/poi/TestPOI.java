package com.nancal.excel.poi;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestPOI {
	
	@Test
	public void testExportExcel() throws Exception{
		ExportExcel export = new ExportExcel();
		
		List<String[]> resources = new ArrayList<String[]>();
		resources.add(new String[]{"1", "客户关系管理系统", "/root", "客户关系管理系统", "T", "0"});
		resources.add(new String[]{"2", "用户管理", "/user", "用户管理", "F", "1"});
		resources.add(new String[]{"3", "客户管理", "/customer", "客户管理", "F", "1"});
		resources.add(new String[]{"4", "系统管理", "/system", "系统管理", "F", "1"});
		
		OutputStream out = new FileOutputStream("D:\\nancal\\test\\bb.xls");
		
		export.exportExcelFile(resources, out);
	}
	
	@Test
	public void testParseExcel() throws Exception{
		ParseExcel parser = new ParseExcel();
		InputStream is = new FileInputStream("D:\\nancal\\test\\bb.xls");
		List<String[]> result = parser.parseExcel(is, "xls", 1);
		for(String[] temp : result){
			System.out.println(Arrays.toString(temp));
		}
	}

}
