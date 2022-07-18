package com.quanjiawei.utils;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class POITest {

    @Test
    public void  readXmlTest() throws IOException {
        String path = POITest.class.getClassLoader().getResource("API_19_DS2_zh_excel_v2_4162642.xls").getPath();
        System.out.println(path);

        //XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(path)));
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(new File(path)));
        HSSFSheet data = hssfWorkbook.getSheet("Data");
        for (Row row : data) {
            for (Cell cell : row) {
                System.out.print(cell+"\t");
            }
            System.out.println();
        }
        hssfWorkbook.close();
    }

    @Test
    public void writeXmlTest() throws IOException, URISyntaxException {
        URL uri = this.getClass().getResource("/");

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet sheet = xssfWorkbook.createSheet("sheet1");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("手机号");

        for (int i = 1; i < 100; i++) {
            XSSFRow dataRow = sheet.createRow(i);
            dataRow.createCell(0).setCellValue("zhangsan"+i);
            dataRow.createCell(1).setCellValue("1333333333"+i);
        }
        System.out.println(uri);
        assert uri != null;
        File file = new File(uri.getPath() +"aaa.xlsx");
        if (!file.exists()){
            System.out.println(file);
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        xssfWorkbook.write(fileOutputStream);
        fileOutputStream.flush();
        xssfWorkbook.close();

    }
}
