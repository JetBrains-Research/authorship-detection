package com.newweb.service.business.imp;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.stereotype.Component;

import com.newweb.service.business.DocumentService;
import com.newweb.util.DateUtil;

@SuppressWarnings("deprecation")
@Component("documentService")
public class DocumentServiceImp implements DocumentService {

	@Override
	public String exportReportToExcel(List<Map<String, Object>> records,String path) {
		String fileName = path +"report.xls";
		try {
			HSSFWorkbook book = new HSSFWorkbook();
			FileOutputStream fileOut = new FileOutputStream(fileName);
			
			HSSFSheet sheet = book.createSheet("新世纪报表");
			
			HSSFRow title = sheet.createRow(0);
			String[] titleCells = {"客户","联系电话","全部账单金额","全部账单应收金额","全部实收总额","欠款总额"};
			CellStyle titleStyle = book.createCellStyle();
			titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
			Font titleFont = book.createFont();
			titleStyle.setFont(titleFont);
			for(int i = 0;i<titleCells.length;i++){
				int index = title.getPhysicalNumberOfCells();
				HSSFCell cell =title.createCell(index);
				cell.setCellStyle(titleStyle);
				cell.setCellValue(titleCells[i]);
			}
			
			sheet.createRow(sheet.getLastRowNum() + 1);
			HSSFCell dateCell = sheet.createRow(sheet.getLastRowNum() + 1).createCell(0);
			CellStyle dateStyle = book.createCellStyle();
			dateStyle.setAlignment(CellStyle.ALIGN_CENTER);
			dateCell.setCellValue("报表导出日期：" + DateUtil.getLocationCurrentDate());
			sheet.addMergedRegion(new Region(dateCell.getRowIndex(), (short)dateCell.getColumnIndex(), dateCell.getRowIndex(), (short)5));
			dateCell.setCellStyle(dateStyle);
			sheet.createRow(sheet.getLastRowNum() + 1);
			
			for(int i = 0;i<records.size();i++){
				Map<String,Object> map = records.get(i);
				HSSFRow content = sheet.createRow(sheet.getLastRowNum()+1);
				String[] contentStrings = {(String)map.get("customer"),(String)map.get("contact")};
				for (String string : contentStrings) {
					CellStyle style = book.createCellStyle();
					style.setAlignment(CellStyle.ALIGN_CENTER);
					int index = content.getPhysicalNumberOfCells();
					HSSFCell cell = content.createCell(index);
					cell.setCellValue(string);
					cell.setCellStyle(style);
				}
				Double[] contentDoubles = {(Double)map.get("allAccount"),(Double)map.get("allRece"),(Double)map.get("allRealIn"),(Double)map.get("owe")};
				for (Double dou : contentDoubles) {
					CellStyle style = book.createCellStyle();
					style.setAlignment(CellStyle.ALIGN_RIGHT);
					int index = content.getPhysicalNumberOfCells();
					HSSFCell cell = content.createCell(index);
					cell.setCellValue(dou);
					cell.setCellStyle(style);
				}
			}
			
			book.write(fileOut);//把book对象输出到文件中  
			fileOut.close();  
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
