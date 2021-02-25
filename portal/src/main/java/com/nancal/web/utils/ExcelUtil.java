package com.nancal.web.utils;

import com.nancal.common.annotation.Excel;
import com.nancal.common.annotation.Excel.Type;
import com.nancal.common.annotation.Excels;
import com.nancal.common.exception.ServiceException;
import com.nancal.common.utils.DateUtils;
import com.nancal.common.utils.StringUtils;
import com.nancal.common.utils.reflect.ReflectUtils;
import com.nancal.common.utils.text.Convert;
import com.nancal.entity.uc.DemoData;
import com.nancal.web.base.AjaxResult;
import com.nancal.web.uc.dao.TaskDao;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Excel相关处理
 *
 * @author
 */
@Component
public class ExcelUtil<T> {
    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * Excel sheet最大行数，默认65536
     */
    public static final int sheetSize = 65536;

    /**
     * 工作表名称
     */
    private String sheetName;

    /**
     * 导出类型（EXPORT:导出数据；IMPORT：导入模板）
     */
    private Type type;

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    /**
     * 导入导出数据列表
     */
    private List<T> list;

    /**
     * 注解列表
     */
    private List<Object[]> fields;

    /**
     * 实体对象
     */

    public Class<T> clazz;

    public ExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ExcelUtil() {
    }

    public void init(List<T> list, String sheetName, Type type) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        createExcelField();
        createWorkbook();
    }

    /**
     * 对excel表单默认第一个索引名转换成list
     *
     * @param is 输入流
     * @return 转换后集合
     */
    public  List<T> importExcel(InputStream is) throws Exception {
        return importExcel(StringUtils.EMPTY, is);
    }

    /**
     * 对excel表单指定表格索引名转换成list
     *
     * @param sheetName 表格索引名
     * @param is        输入流
     * @return 转换后集合
     */
    public List<T> importExcel(String sheetName, InputStream is) throws Exception {
        this.type = Type.IMPORT;
        this.wb = WorkbookFactory.create(is);
        List<T> list = new ArrayList<T>();
        Sheet sheet = null;
        if (StringUtils.isNotEmpty(sheetName)) {
            // 如果指定sheet名,则取指定sheet中的内容.
            sheet = wb.getSheet(sheetName);
        } else {
            // 如果传入的sheet名不存在则默认指向第1个sheet.
            sheet = wb.getSheetAt(0);
        }

        if (sheet == null) {
            throw new IOException("文件sheet不存在");
        }

        int rows = sheet.getPhysicalNumberOfRows();

        if (rows > 0) {
            // 定义一个map用于存放excel列的序号和field.
            Map<String, Integer> cellMap = new HashMap<String, Integer>();
            // 获取表头
            Row heard = sheet.getRow(0);
            for (int i = 0; i < heard.getPhysicalNumberOfCells(); i++) {
                Cell cell = heard.getCell(i);
                if (StringUtils.isNotNull(cell)) {
                    String value = this.getCellValue(heard, i).toString();
                    cellMap.put(value, i);
                } else {
                    cellMap.put(null, i);
                }
            }
            // 有数据时才处理 得到类的所有field.
            Field[] allFields = clazz.getDeclaredFields();
            // 定义一个map用于存放列的序号和field.
            Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
            for (int col = 0; col < allFields.length; col++) {
                Field field = allFields[col];
                Excel attr = field.getAnnotation(Excel.class);
                if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
                    // 设置类的私有字段属性可访问.
                    field.setAccessible(true);
                    Integer column = cellMap.get(attr.name());
                    fieldsMap.put(column, field);
                }
            }
            for (int i = 1; i < rows; i++) {
                // 从第2行开始取数据,默认第一行是表头.
                Row row = sheet.getRow(i);
                T entity = null;
                for (Map.Entry<Integer, Field> entry : fieldsMap.entrySet()) {
                    Object val = this.getCellValue(row, entry.getKey());

                    // 如果不存在实例则新建.
                    entity = (entity == null ? clazz.newInstance() : entity);
                    // 从map中得到对应列的field.
                    Field field = fieldsMap.get(entry.getKey());
                    // 取得类型,并根据对象类型设置值.
                    Class<?> fieldType = field.getType();
                    if (String.class == fieldType) {
                        String s = Convert.toStr(val);
                        if (StringUtils.endsWith(s, ".0")) {
                            val = StringUtils.substringBefore(s, ".0");
                        } else {
                            val = Convert.toStr(val);
                        }
                    } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                        val = Convert.toInt(val);
                    } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                        val = Convert.toLong(val);
                    } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                        val = Convert.toDouble(val);
                    } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                        val = Convert.toFloat(val);
                    } else if (BigDecimal.class == fieldType) {
                        val = Convert.toBigDecimal(val);
                    } else if (Date.class == fieldType) {
                        if (val instanceof String) {
                            val = DateUtils.parseDate(val);
                        } else if (val instanceof Double) {
                            val = DateUtil.getJavaDate((Double) val);
                        }
                    }
                    if (StringUtils.isNotNull(fieldType)) {
                        Excel attr = field.getAnnotation(Excel.class);
                        String propertyName = field.getName();
                        if (StringUtils.isNotEmpty(attr.targetAttr())) {
                            propertyName = field.getName() + "." + attr.targetAttr();
                        } else if (StringUtils.isNotEmpty(attr.readConverterExp())) {
                            val = reverseByExp(String.valueOf(val), attr.readConverterExp());
                        }
                        ReflectUtils.invokeSetter(entity, propertyName, val);
                    }
                }
                list.add(entity);
            }
        }
        return list;
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public AjaxResult exportExcel(List<T> list, String sheetName) {
        this.init(list, sheetName, Type.EXPORT);
        return exportExcel();
    }


    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public void exportExcel(List<T> list, String sheetName, HttpServletRequest request, HttpServletResponse response) {
        this.init(list, sheetName, Type.EXPORT);
        exportExcel(request, response);
    }

    public void exportExcel(String date, List<T> list, String sheetName, HttpServletRequest request, HttpServletResponse response) {
        this.init(list, sheetName, Type.EXPORT);
        exportExcel(date, request, response);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public AjaxResult importTemplateExcel(String sheetName) {
        this.init(null, sheetName, Type.IMPORT);
        return exportExcel();
    }

    /**
     * 对list数据源将其里面的数据导出到excel表单
     *
     * @return 结果
     */
    public AjaxResult exportExcel() {
        OutputStream out = null;
        try {
            // 取出一共有多少个sheet.
            double sheetNo = Math.ceil(list.size() / sheetSize);
            for (int index = 0; index <= sheetNo; index++) {
                createSheet(sheetNo, index);

                // 产生一行
                Row row = sheet.createRow(0);
                int column = 0;
                // 写入各个字段的列头名称
                for (Object[] os : fields) {
                    Excel excel = (Excel) os[1];
                    this.createCell(excel, row, column++);
                }
                if (Type.EXPORT.equals(type)) {
                    fillExcelData(index, row);
                }
            }
            String filename = encodingFilename(sheetName);
            //  out = new FileOutputStream(getAbsoluteFile(filename));
//            out = response.getOutputStream();


            wb.write(out);

            return AjaxResult.success(filename);
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
            throw new ServiceException("导出Excel失败，请联系网站管理员！");
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @return 结果
     */
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        OutputStream out = null;
        try {
            // 取出一共有多少个sheet.
            double sheetNo = Math.ceil(list.size() / sheetSize);
            for (int index = 0; index <= sheetNo; index++) {
                createSheet(sheetNo, index);

                // 产生一行
                Row row = sheet.createRow(0);
                int column = 0;
                // 写入各个字段的列头名称
                for (Object[] os : fields) {
                    Excel excel = (Excel) os[1];
                    this.createCell(excel, row, column++);
                }
                if (Type.EXPORT.equals(type)) {
                    fillExcelData(index, row);
                }
            }
            String fileName = encodingFilename(sheetName);
//            String fileName = encodingFilename(sheetName);

            out = response.getOutputStream();

            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setContentType("application/octet-stream");
            response.setHeader("Pragma", "public");


            String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            String agent = request.getHeader("USER-AGENT").toLowerCase();
            if (agent.contains("firefox")) {
                response.setCharacterEncoding("utf-8");
                response.setHeader("content-disposition",
                        "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1") + ".xlsx");
            } else {
                response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
            }


            wb.write(out);

        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
            throw new ServiceException("导出Excel失败，请联系网站管理员！");
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @return 结果
     */
    public void exportExcel(String date, HttpServletRequest request, HttpServletResponse response) {
        OutputStream out = null;
        try {
            // 取出一共有多少个sheet.
            double sheetNo = Math.ceil(list.size() / sheetSize);
            for (int index = 0; index <= sheetNo; index++) {
                createSheet(sheetNo, index);

                // 产生一行
                Row row = sheet.createRow(0);
                int column = 0;
                // 写入各个字段的列头名称
                for (Object[] os : fields) {
                    Excel excel = (Excel) os[1];
                    this.createCell(excel, row, column++);
                }
                if (Type.EXPORT.equals(type)) {
                    fillExcelData(index, row);
                }
            }
            String fileName = encodingFilenames(date, sheetName);

            out = response.getOutputStream();

            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setContentType("application/octet-stream");
            response.setHeader("Pragma", "public");


            String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            String agent = request.getHeader("USER-AGENT").toLowerCase();
            if (agent.contains("firefox")) {
                response.setCharacterEncoding("utf-8");
                response.setHeader("content-disposition",
                        "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1") + ".xlsx");
            } else {
                response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
            }


            wb.write(out);

        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
            throw new ServiceException("导出Excel失败，请联系网站管理员！");
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    /**
     * 填充excel数据
     *
     * @param index 序号
     * @param row   单元格行
     */
    public void fillExcelData(int index, Row row) {
        int startNo = index * sheetSize;
        int endNo = Math.min(startNo + sheetSize, list.size());
        for (int i = startNo; i < endNo; i++) {
            row = sheet.createRow(i + 1 - startNo);
            // 得到导出对象.
            T vo = (T) list.get(i);
            int column = 0;
            for (Object[] os : fields) {
                Field field = (Field) os[0];
                Excel excel = (Excel) os[1];
                // 设置实体类私有属性可访问
                field.setAccessible(true);
                this.addCell(excel, row, vo, field, column++);
            }
        }
    }

    /**
     * 创建表格样式
     *
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private Map<String, CellStyle> createStyles(Workbook wb) {
        // 写入各条记录,每条记录对应excel表中的一行
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);

        return styles;
    }

    /**
     * 创建单元格
     */
    public Cell createCell(Excel attr, Row row, int column) {
        // 创建列
        Cell cell = row.createCell(column);
        // 写入列信息
        cell.setCellValue(attr.name());
        setDataValidation(attr, row, column);
        cell.setCellStyle(styles.get("header"));
        return cell;
    }

    /**
     * 设置单元格信息
     *
     * @param value 单元格值
     * @param attr  注解相关
     * @param cell  单元格信息
     */
    public void setCellVo(Object value, Excel attr, Cell cell) {
        if (Excel.ColumnType.STRING == attr.cellType()) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(StringUtils.isNull(value) ? attr.defaultValue() : value + attr.suffix());
        } else if (Excel.ColumnType.NUMERIC == attr.cellType()) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(Integer.parseInt(value + ""));
        }
    }

    /**
     * 创建表格样式
     */
    public void setDataValidation(Excel attr, Row row, int column) {
        if (attr.name().indexOf("注：") >= 0) {
            sheet.setColumnWidth(column, 6000);
        } else {
            // 设置列宽
            sheet.setColumnWidth(column, (int) ((attr.width() + 0.72) * 256));
            row.setHeight((short) (attr.height() * 20));
        }
        // 如果设置了提示信息则鼠标放上去提示.
        if (StringUtils.isNotEmpty(attr.prompt())) {
            // 这里默认设了2-101列提示.
            setXSSFPrompt(sheet, "", attr.prompt(), 1, 100, column, column);
        }
        // 如果设置了combo属性则本列只能选择不能输入
        if (attr.combo().length > 0) {
            // 这里默认设了2-101列只能选择不能输入.
            setXSSFValidation(sheet, attr.combo(), 1, 100, column, column);
        }
    }

    /**
     * 添加单元格
     */
    public Cell addCell(Excel attr, Row row, T vo, Field field, int column) {
        Cell cell = null;
        try {
            // 设置行高
            row.setHeight((short) (attr.height() * 20));
            // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
            if (attr.isExport()) {
                // 创建cell
                cell = row.createCell(column);
                cell.setCellStyle(styles.get("data"));

                // 用于读取对象中的属性
                Object value = getTargetValue(vo, field, attr);
                String dateFormat = attr.dateFormat();
                String readConverterExp = attr.readConverterExp();
                if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(DateUtils.parseDateToStr(dateFormat, (Date) value));
                } else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(convertByExp(String.valueOf(value), readConverterExp));
                } else {
                    // 设置列类型
                    setCellVo(value, attr, cell);
                }
            }
        } catch (Exception e) {
            log.error("导出Excel失败{}", e);
        }
        return cell;
    }

    /**
     * 设置 POI XSSFSheet 单元格提示
     *
     * @param sheet         表单
     * @param promptTitle   提示标题
     * @param promptContent 提示内容
     * @param firstRow      开始行
     * @param endRow        结束行
     * @param firstCol      开始列
     * @param endCol        结束列
     */
    public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
                              int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        dataValidation.createPromptBox(promptTitle, promptContent);
        dataValidation.setShowPromptBox(true);
        sheet.addValidationData(dataValidation);
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet    要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow   结束行
     * @param firstCol 开始列
     * @param endCol   结束列
     * @return 设置好的sheet.
     */
    public void setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        // 加载下拉列表内容
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }

        sheet.addValidationData(dataValidation);
    }

    /**
     * 解析导出值 0=男,1=女,2=未知
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @return 解析后值
     * @throws Exception
     */
    public static String convertByExp(String propertyValue, String converterExp) throws Exception {
        try {
            String[] convertSource = converterExp.split(",");
            for (String item : convertSource) {
                String[] itemArray = item.split("=");
                if (itemArray[0].equals(propertyValue)) {
                    return itemArray[1];
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return propertyValue;
    }

    /**
     * 反向解析值 男=0,女=1,未知=2
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @return 解析后值
     * @throws Exception
     */
    public static String reverseByExp(String propertyValue, String converterExp) throws Exception {
        try {
            String[] convertSource = converterExp.split(",");
            for (String item : convertSource) {
                String[] itemArray = item.split("=");
                if (itemArray[1].equals(propertyValue)) {
                    return itemArray[0];
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return propertyValue;
    }

    /**
     * 编码文件名
     */
    public String encodingFilename(String filename) {
        filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
        return filename;
    }

    /**
     * 编码文件名
     */
    public String encodingFilenames(String date, String filename) {
        filename = date + "_" + filename;
        return filename;
    }

    /**
     * 获取下载路径
     *
     * @param filename 文件名称
     */
//    public String getAbsoluteFile(String filename) {
//        String downloadPath = CardConfig.getDownloadPath() + filename;
//        File desc = new File(downloadPath);
//        if (!desc.getParentFile().exists()) {
//            desc.getParentFile().mkdirs();
//        }
//        return downloadPath;
//    }

    /**
     * 获取bean中的属性值
     *
     * @param vo    实体对象
     * @param field 字段
     * @param excel 注解
     * @return 最终的属性值
     * @throws Exception
     */
    private Object getTargetValue(T vo, Field field, Excel excel) throws Exception {
        Object o = field.get(vo);
        if (StringUtils.isNotEmpty(excel.targetAttr())) {
            String target = excel.targetAttr();
            if (target.indexOf(".") > -1) {
                String[] targets = target.split("[.]");
                for (String name : targets) {
                    o = getValue(o, name);
                }
            } else {
                o = getValue(o, target);
            }
        }
        return o;
    }

    /**
     * 以类的属性的get方法方法形式获取值
     *
     * @param o
     * @param name
     * @return value
     * @throws Exception
     */
    private Object getValue(Object o, String name) throws Exception {
        if (StringUtils.isNotEmpty(name)) {
            Class<?> clazz = o.getClass();
            String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = clazz.getMethod(methodName);
            o = method.invoke(o);
        }
        return o;
    }

    /**
     * 得到所有定义字段
     */
    private void createExcelField() {
        this.fields = new ArrayList<Object[]>();
        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        for (Field field : tempFields) {
            // 单注解
            if (field.isAnnotationPresent(Excel.class)) {
                putToField(field, field.getAnnotation(Excel.class));
            }

            // 多注解
            if (field.isAnnotationPresent(Excels.class)) {
                Excels attrs = field.getAnnotation(Excels.class);
                Excel[] excels = attrs.value();
                for (Excel excel : excels) {
                    putToField(field, excel);
                }
            }
        }
    }

    /**
     * 放到字段集合中
     */
    private void putToField(Field field, Excel attr) {
        if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
            this.fields.add(new Object[]{field, attr});
        }
    }

    /**
     * 创建一个工作簿
     */
    public void createWorkbook() {
        this.wb = new SXSSFWorkbook(500);
    }

    /**
     * 创建工作表
     *
     * @param sheetNo sheet数量
     * @param index   序号
     */
    public void createSheet(double sheetNo, int index) {
        this.sheet = wb.createSheet();
        this.styles = createStyles(wb);
        // 设置工作表的名称.
        if (sheetNo == 0) {
            wb.setSheetName(index, sheetName);
        } else {
            wb.setSheetName(index, sheetName + index);
        }
    }

    /**
     * 获取单元格值
     *
     * @param row    获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public Object getCellValue(Row row, int column) {
        if (row == null) {
            return row;
        }
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            if (StringUtils.isNotNull(cell)) {
                if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {
                    val = cell.getNumericCellValue();
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        val = DateUtil.getJavaDate((Double) val); // POI Excel 日期格式转换
                    } else {
                        if ((Double) val % 1 > 0) {
                            val = new DecimalFormat("0.00").format(val);
                        } else {
                            val = new DecimalFormat("0").format(val);
                        }
                    }
                } else if (cell.getCellTypeEnum() == CellType.STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellTypeEnum() == CellType.ERROR) {
                    val = cell.getErrorCellValue();
                }

            }
        } catch (Exception e) {
            return val;
        }
        return val;
    }

    public List<DemoData> importExcelUser(InputStream is) throws Exception {
        this.wb = WorkbookFactory.create(is);
        List<DemoData> list = new ArrayList<DemoData>();
        Sheet sheet = null;
        if (StringUtils.isNotEmpty(sheetName)) {
            // 如果指定sheet名,则取指定sheet中的内容.
            sheet = wb.getSheet(sheetName);
        } else {
            // 如果传入的sheet名不存在则默认指向第1个sheet.
            sheet = wb.getSheetAt(0);
        }
        if (sheet == null) {
            throw new IOException("文件sheet不存在");
        }

        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 1; i < rows; i++) {
            // 从第2行开始取数据,默认第一行是表头.
            Row row = sheet.getRow(i);
            DemoData demoData = new DemoData();
            if (row.getCell(0) != null) {
                row.getCell(0).setCellType(CellType.STRING);
                demoData.setOrderNo(row.getCell(0).toString());
                row.getCell(1).setCellType(CellType.STRING);
                demoData.setSortie(row.getCell(1).toString());

                row.getCell(2).setCellType(CellType.STRING);
                demoData.setXbomNumber(row.getCell(2).toString());


                row.getCell(3).setCellType(CellType.STRING);
                demoData.setXbomNumber(row.getCell(3).toString());

                row.getCell(4).getNumericCellValue();
                demoData.setPlanQty(Integer.parseInt(row.getCell(4).toString()));

                row.getCell(5).setCellType(CellType.STRING);
                demoData.setBatch(row.getCell(5).toString());

                row.getCell(6).setCellType(CellType.STRING);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                demoData.setStartDate(format.parse(row.getCell(6).toString()));

                row.getCell(7).setCellType(CellType.STRING);
                demoData.setEndDate(format.parse(row.getCell(7).toString()));

                row.getCell(8).setCellType(CellType.STRING);
                demoData.setProduct(row.getCell(8).toString());

                //客户名称
                if (row.getCell(9) != null) {
                    String customer = row.getCell(9).toString();
                    demoData.setCustomer(customer);
                }
                list.add(demoData);
            }

        }
        return list;
    }

    @Resource
    private TaskDao taskDao;

    public int importTaskExcel(MultipartFile file) throws IOException, ParseException {
        int result = 0;
        List<DemoData> demoDataList = new ArrayList<>();
        String filename = file.getOriginalFilename();
        String s = filename.substring(filename.lastIndexOf(".") + 1);
        System.out.println(s);
        InputStream inputStream = file.getInputStream();
        Workbook workbook = null;
        if (s.equals("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            workbook = new HSSFWorkbook(inputStream);
        }
        Sheet sheetAt = workbook.getSheetAt(0);
        if (sheetAt != null) {
            for (int line = 1; line <= sheetAt.getLastRowNum(); line++
            ) {
                DemoData demoData1 = new DemoData();
                Row row = sheetAt.getRow(line);
                if (null == row) {
                    continue;
                }
                String orderNo = row.getCell(0).toString();
                demoData1.setOrderNo(orderNo);
                String sortie = row.getCell(1).toString();
                demoData1.setSortie(sortie);
                //XBOM号
                String xbomNumber = row.getCell(2).toString();
                demoData1.setXbomNumber(xbomNumber);
                //产品名称
                String productName = row.getCell(3).toString();
                demoData1.setProductName(productName);
                //计划数量
                double planQty = row.getCell(4).getNumericCellValue();
                demoData1.setPlanQty((int) planQty);
                //批次
                String batch = row.getCell(5).toString();
                demoData1.setBatch(batch);
                //计划开始时间
                String dateCellValue = row.getCell(6).toString();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = format.parse(dateCellValue);
                demoData1.setStartDate(startDate);
                //计划结束时间
                String endDate = row.getCell(7).toString();
                Date endDates = format.parse(endDate);
                demoData1.setEndDate(endDates);
                //图号
                String product = row.getCell(8).toString();
                demoData1.setProduct(product);
                //客户名称
                if (row.getCell(9) != null) {
                    String customer = row.getCell(9).toString();
                    demoData1.setCustomer(customer);
                }
                demoDataList.add(demoData1);
//                List<DemoData> orderNos = taskDao.findByOrderNo(demoData1.getOrderNo());
//                if (orderNos != null && orderNos.size() > 0) {
//                    throw new RuntimeException("订单重复");
//                }
            }
            for (DemoData demoData : demoDataList) {
//                List<DemoData> byOrderNo = taskDao.findByOrderNo(demoData.getOrderNo());
//                if (byOrderNo != null) {
                demoData.setCreateByCode("XF_MES");
                // demoData.setCreateByName(AppUserUtil.getLoginAppUser().getNickname());
                demoData.setCreateTime(new Date());
                //   taskDao.save(demoData);
//                } else {
//                    DemoData demoData1 = new DemoData();
//                    CopyUtils.objectToObject(demoData,demoData1);
////                    result = taskDao.updateDemoData(demoData,byOrderNo.get(0).getMesTaskId());
//                    taskDao.save(demoData1);
//                }
            }
        }
        return result;
    }

    public static XSSFWorkbook readExcel(List<DemoData> demoData) {
        InputStream resourceAsStream = ExcelUtil.class.getResourceAsStream("/db/demodata.xlsx");
        int rowNum = 1;
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(resourceAsStream);
            XSSFSheet sheetAt = wb.getSheetAt(0);
            for (DemoData d : demoData) {
                XSSFRow row = sheetAt.createRow(rowNum++);
                row.createCell(0).setCellValue(d.getOrderNo());
                row.createCell(1).setCellValue(d.getSortie());
                row.createCell(2).setCellValue(d.getXbomNumber());
                row.createCell(3).setCellValue(d.getProductName());
                row.createCell(4).setCellValue(d.getPlanQty());
                row.createCell(5).setCellValue(d.getBatch());
                row.createCell(6).setCellValue(d.getStartDate());
                row.createCell(7).setCellValue(d.getEndDate());
                row.createCell(8).setCellValue(d.getCustomer());
                row.createCell(9).setCellValue(d.getProduct());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resourceAsStream != null) {
                    resourceAsStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wb;
    }

    public static void exportExcelFile(List<DemoData> resources, OutputStream out) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("sheet1");

        // 列宽
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.setColumnWidth(7, 3550);
        sheet.autoSizeColumn(8);
        sheet.autoSizeColumn(9);

        // 表头
        HSSFRow headerRow = sheet.createRow(0);
        // 表头样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        //自动换行
        headerStyle.setWrapText(false);

        // 表头字体
        HSSFFont headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(HSSFColor.BLACK.index);
        headerFont.setFontName("黑体");
        headerStyle.setFont(headerFont);

        HSSFCell cell = null;
        cell = headerRow.createCell(0);
        cell.setCellValue("任务号");
        cell.setCellStyle(headerStyle);

        cell = headerRow.createCell(1);
        cell.setCellValue("架次号");
        cell.setCellStyle(headerStyle);

        cell = headerRow.createCell(2);
        cell.setCellValue("XBOM号");
        cell.setCellStyle(headerStyle);

        cell = headerRow.createCell(3);
        cell.setCellValue("产品名称");
        cell.setCellStyle(headerStyle);

        cell = headerRow.createCell(4);
        cell.setCellValue("计划数量");
        cell.setCellStyle(headerStyle);

        cell = headerRow.createCell(5);
        cell.setCellValue("批次");
        cell.setCellStyle(headerStyle);

        cell = headerRow.createCell(6);
        cell.setCellValue("计划开始时间");
        cell.setCellStyle(headerStyle);

        cell = headerRow.createCell(7);
        cell.setCellValue("计划结束时间");
        cell.setCellStyle(headerStyle);

        cell = headerRow.createCell(8);
        cell.setCellValue("产品图号");
        cell.setCellStyle(headerStyle);

        cell = headerRow.createCell(9);
        cell.setCellValue("客户名称");
        cell.setCellStyle(headerStyle);

        // 表体样式
        HSSFCellStyle bodyStyle = workbook.createCellStyle();
        bodyStyle.setAlignment(HorizontalAlignment.CENTER);
        // 表体字体
        HSSFFont bodyFont = workbook.createFont();
        bodyFont.setColor(HSSFColor.BLACK.index);
        bodyFont.setFontHeightInPoints((short) 10);
        bodyFont.setFontName("宋体");

        bodyStyle.setFont(bodyFont);
        if (resources != null && resources.size() > 0) {
            HSSFRow bodyRow = null;
            HSSFCell bodyCell = null;
            int rowNum = 1;
            for (DemoData demodata : resources) {
                bodyRow = sheet.createRow(rowNum++);

                bodyCell = bodyRow.createCell(0);
                bodyCell.setCellValue(String.valueOf(demodata.getOrderNo()));
                bodyCell.setCellStyle(bodyStyle);

                bodyCell = bodyRow.createCell(1);
                bodyCell.setCellValue(String.valueOf(demodata.getSortie()));
                bodyCell.setCellStyle(bodyStyle);

                bodyCell = bodyRow.createCell(2);
                bodyCell.setCellValue(String.valueOf(demodata.getXbomNumber()));
                bodyCell.setCellStyle(bodyStyle);

                bodyCell = bodyRow.createCell(3);
                bodyCell.setCellValue(String.valueOf(demodata.getProductName()));
                bodyCell.setCellStyle(bodyStyle);

                bodyCell = bodyRow.createCell(4);
                bodyCell.setCellValue(String.valueOf(demodata.getPlanQty()));
                bodyCell.setCellStyle(bodyStyle);

                bodyCell = bodyRow.createCell(5);
                bodyCell.setCellValue(String.valueOf(demodata.getBatch()));
                bodyCell.setCellStyle(bodyStyle);
                bodyCell = bodyRow.createCell(6);
                bodyCell.setCellValue(String.valueOf(demodata.getStartDate()));
                bodyCell.setCellStyle(bodyStyle);
                bodyCell = bodyRow.createCell(7);
                bodyCell.setCellValue(String.valueOf(demodata.getEndDate()));
                bodyCell.setCellStyle(bodyStyle);
                bodyCell = bodyRow.createCell(8);
                bodyCell.setCellValue(String.valueOf(demodata.getCustomer()));
                bodyCell.setCellStyle(bodyStyle);
                bodyCell = bodyRow.createCell(9);
                bodyCell.setCellValue(String.valueOf(demodata.getProduct()));
                bodyCell.setCellStyle(bodyStyle);
            }
        }
        // 输出
        workbook.write(out);
        workbook.close();
    }

    /**
     * 文件导出方法.
     *
     * @param resource     List<String[]> 集合类型, 要导出的具体数据集合.
     * @param outputStream 输出流. 输出的excel文件保存的具体位置.
     * @throws IOException
     */
    public void exportExcel(List<DemoData> resource, OutputStream outputStream)
            throws IOException {

        // 创建一个内存Excel对象.
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 创建一个表格.
        HSSFSheet sheet = workbook.createSheet("sheet1");

        // 创建表头
        // 获取表头内容.
        DemoData headerStr = resource.get(0);
        HSSFRow headerRow = sheet.createRow(0);

        // 设置列宽

        // 设置头单元格样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER); // 水平居中
        // 设置字体
        HSSFFont headerFont = workbook.createFont();
        headerFont.setColor(HSSFColor.VIOLET.index);
        headerFont.setFontName("宋体");
        headerStyle.setFont(headerFont);

        // 定义表头内容.

        // 创建一个单元格
//            HSSFCell headerCell = headerRow.createCell(i);
//            headerCell.setCellStyle(headerStyle);
//            headerCell.setCellValue(headerStr[i]);
        HSSFSheet sheetAt = workbook.createSheet();
        int rowNum = 1;
        for (DemoData d : resource) {
            HSSFRow row = sheetAt.createRow(rowNum++);
            row.createCell(0).setCellValue(d.getOrderNo());
            row.createCell(1).setCellValue(d.getSortie());
            row.createCell(2).setCellValue(d.getXbomNumber());
            row.createCell(3).setCellValue(d.getProductName());
            row.createCell(4).setCellValue(d.getPlanQty());
            row.createCell(5).setCellValue(d.getBatch());
            row.createCell(6).setCellValue(d.getStartDate());
            row.createCell(7).setCellValue(d.getEndDate());
            row.createCell(8).setCellValue(d.getCustomer());
            row.createCell(9).setCellValue(d.getProduct());
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

        for (int row = 1; row < resource.size(); row++) {
            // 输出的行数据
            DemoData data = resource.get(row);
            // 创建行
            HSSFRow bodyRow = sheet.createRow(row);
            // 循环创建列
            for (int cell = 0; cell < bodyRow.getLastCellNum(); cell++) {
                HSSFCell bodyCell = bodyRow.createCell(cell);
                bodyCell.setCellStyle(bodyStyle);
                bodyCell.setCellValue(resource.add(data));
            }
        }

        // 将内存创建的excel对象,输出到文件中.
        workbook.write(outputStream);

    }

    /**
     * 解析excel
     */
    public static List<String[]> parseExcel(InputStream is, String suffix, int startRow)
            throws IOException {

        Workbook workbook = null;
        if ("xls".equals(suffix)) {
            workbook = new HSSFWorkbook(is);
        } else if ("xlsx".equals(suffix)) {
            workbook = new XSSFWorkbook(is);
        } else {
            return null;
        }

        Sheet sheet = workbook.getSheetAt(0);

        if (sheet == null) {
            return null;
        }

        int lastRowNum = sheet.getLastRowNum();
        List<String[]> list = new ArrayList<String[]>();

        for (int rowNum = startRow; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                short firstCellNum = row.getFirstCellNum();
                short lastCellNum = row.getLastCellNum();
                if (firstCellNum != lastCellNum) {
                    String[] rowArray = new String[lastCellNum];
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        if (cell == null) {
                            rowArray[cellNum] = "";
                        } else {
                            rowArray[cellNum] = parseCell(cell);
                        }
                    }

                    list.add(rowArray);
                }
            }
        }

        return list;
    }

    private static String parseCell(Cell cell) {
        String cellStr = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC: // 数学
                if (HSSFDateUtil.isCellDateFormatted(cell)) { // 日期,时间
                    SimpleDateFormat sdf = null;
                    if (cell.getCellStyle().getDataFormat() ==
                            HSSFDataFormat.getBuiltinFormat("h:mm")) { // 时间 HH:mm
                        sdf = new SimpleDateFormat("HH:mm");
                    } else {
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    cellStr = sdf.format(cell.getDateCellValue());
                } else if (cell.getCellStyle().getDataFormat() == 58) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double temp = cell.getNumericCellValue();
                    Date date = DateUtil.getJavaDate(temp);
                    cellStr = sdf.format(date);
                } else {
                    double temp = cell.getNumericCellValue();
                    String style = cell.getCellStyle().getDataFormatString();
                    DecimalFormat format = new DecimalFormat();
                    if (style.equals("General")) {
                        format.applyPattern("#");
                    }
                    cellStr = format.format(temp);
                }
                break;
            case HSSFCell.CELL_TYPE_STRING: // 字符串
                cellStr = cell.getRichStringCellValue().toString();
                break;
            case HSSFCell.CELL_TYPE_BLANK: // 布尔
                cellStr = "";
                break;
            default:
                cellStr = "";
        }

        return cellStr;
    }

}
