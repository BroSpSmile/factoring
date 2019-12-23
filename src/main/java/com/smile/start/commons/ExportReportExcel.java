/**
 * com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.smile.start.commons;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;

/**
 * 生成excel工具
 * @author Tiny.Jing
 * @version $Id: ExportReportExcel.java, v 0.1 2016年6月1日 下午5:25:54 ff23098 Exp $
 */
public class ExportReportExcel {

    /**
     * 生成excel对象
     * @param title 工作薄名称
     * @param headers 表头集合
     * @param datas 数据
     * @return
     */
    public static <T> HSSFWorkbook creatExcel(String title, String[] headers, List<T> datas) {
        return exportExcel(title, headers, datas, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 生成excel对象
     * @param title 工作薄名称
     * @param headers 表头集合
     * @param datas 数据
     * @param pattern 日期的格式
     * @return
     */
    public static <T> HSSFWorkbook creatExcel(String title, String[] headers, List<T> datas, String pattern) {
        return exportExcel(title, headers, datas, pattern);
    }

    /**
     * 生成表头以及数据
     * @param title
     * @param headers
     * @param datas
     * @param pattern
     * @return
     */
    @SuppressWarnings({ "deprecation" })
    private static <T> HSSFWorkbook exportExcel(String title, String[] headers, List<T> datas, String pattern) {

        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格   
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((short) 15);
        // 产生表格标题行  
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        // 遍历集合数据，产生数据行  
        for (int j = 0; j < datas.size(); j++) {
            row = sheet.createRow(j + 1);
            T t = datas.get(j);

            Field[] fields = t.getClass().getDeclaredFields();
            //            for (int i = 0, cellIndex = 0; i < fields.length; i++, cellIndex++) {
            for (int i = 0, cellIndex = 0; i < headers.length; i++, cellIndex++) {
                try {
                    if (cellIndex >= headers.length || cellIndex >= fields.length)
                        break;
                    //第一个字段是上面这个那就使用下一个字段给单元格赋值
                    if ("serialVersionUID".equals(fields[cellIndex].getName())) {
                        cellIndex += 1;
                    }
                    HSSFCell cell = row.createCell(i);
                    String fieldName = fields[cellIndex].getName();
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                    Class<?> tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});
                    // 判断值的类型后进行强制类型转换  
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else {
                        // 其它数据类型都当作字符串简单处理  
                        textValue = (value != null ? value.toString() : "");
                    }
                    cell.setCellValue(textValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return workbook;
    }
}
