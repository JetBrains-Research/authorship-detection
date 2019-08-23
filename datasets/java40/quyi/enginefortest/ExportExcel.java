package com.nercis.isscp.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

import android.R.integer;
import jxl.Cell;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * @author gaoyang
 * @date 2012-11-9 上午11:37:17
 * @description
 */
public class ExportExcel {
	/**
	 * @param fileName
	 *            EXCEL文件名称
	 * @param listTitle
	 *            EXCEL文件第一行列标题集合
	 * @param listContent
	 *            EXCEL文件正文数据集合
	 * @return
	 * @throws InterruptedException
	 */
	public void exportToSingleExcel(String fileName,
			SingleRecordInfo singleRecordInfo) {
		try {
			Workbook preWorkbook = Workbook.getWorkbook(new File(fileName));
			WritableWorkbook workbook = Workbook.createWorkbook(new File(
					fileName), preWorkbook);
			/** **********创建工作表************ */
			WritableSheet sheet = workbook.getSheet(0);

			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行
			if (singleRecordInfo.getFinishedTime() == 0) {
				int row = sheet.getRows();
				sheet.addCell(new Label(0, row,
						singleRecordInfo.getUserAppId(), wcf_left));
				sheet.addCell(new Label(1, row,
						singleRecordInfo.getMissionId(), wcf_left));
				sheet.addCell(new Label(2, row, singleRecordInfo.getAppName(),
						wcf_left));
				sheet.addCell(new Label(3, row, singleRecordInfo.getMd5(),
						wcf_left));
				sheet.addCell(new Label(4, row, singleRecordInfo.getAppSize(),
						wcf_left));
				sheet.addCell(new jxl.write.Number(5, row, singleRecordInfo
						.getStartedTime(), wcf_left));
				sheet.addCell(new Label(7, row, singleRecordInfo.getPlotsType()
						.name(), wcf_left));
			} else {
				int row = sheet.getRows();
				/*
				 * List<String> columninfoList = new ArrayList<String>(); for
				 * (int i = 1; i < sheet.getRows(); i++) { // 看md5 Cell cell =
				 * sheet.getCell(3, i); columninfoList.add(cell.getContents());
				 * } int row = columninfoList.indexOf(singleRecordInfo.getMd5())
				 * + 1; if (sheet.getCell(7,
				 * row).getContents().equals(singleRecordInfo
				 * .getPlotsType().name())) { sheet.addCell(new
				 * jxl.write.Number(6, row, singleRecordInfo.getFinishedTime(),
				 * wcf_left)); sheet.addCell(new Label(8, row,
				 * singleRecordInfo.getAppCheckResult(), wcf_left)); } else if
				 * (sheet.getCell(7, row +
				 * 1).getContents().equals(singleRecordInfo
				 * .getPlotsType().name())) { sheet.addCell(new
				 * jxl.write.Number(6, row + 1,
				 * singleRecordInfo.getFinishedTime(), wcf_left));
				 * sheet.addCell(new Label(8, row + 1,
				 * singleRecordInfo.getAppCheckResult(), wcf_left)); }
				 */
				sheet.addCell(new Label(0, row,
						singleRecordInfo.getUserAppId(), wcf_left));
				sheet.addCell(new Label(1, row,
						singleRecordInfo.getMissionId(), wcf_left));
				sheet.addCell(new jxl.write.Number(6, row, singleRecordInfo
						.getFinishedTime(), wcf_left));
				sheet.addCell(new Label(7, row, singleRecordInfo.getPlotsType()
						.name(), wcf_left));
				sheet.addCell(new Label(8, row, singleRecordInfo
						.getAppCheckResult(), wcf_left));

			}

			workbook.write();
			/** *********关闭文件************* */
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exportToSearchExcel(String filePath,
			SearchRecordInfo searchRecordInfo) {
		try {
			Workbook preWorkbook = Workbook.getWorkbook(new File(filePath));
			WritableWorkbook workbook = Workbook.createWorkbook(new File(
					filePath), preWorkbook);
			/** **********创建工作表************ */
			WritableSheet sheet = workbook.getSheet(0);

			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行
			int row = sheet.getRows();
			sheet.addCell(new Label(0, row, searchRecordInfo.getNumber(),
					wcf_left));
			sheet.addCell(new jxl.write.Number(1, row, searchRecordInfo
					.getCount(), wcf_left));
			sheet.addCell(new jxl.write.Number(2, row, searchRecordInfo
					.getStartedTime(), wcf_left));
			sheet.addCell(new jxl.write.Number(3, row, searchRecordInfo
					.getFinishedTime(), wcf_left));
			sheet.addCell(new Label(4, row, searchRecordInfo.getCheckResult(),
					wcf_left));

			workbook.write();
			/** *********关闭文件************* */
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
