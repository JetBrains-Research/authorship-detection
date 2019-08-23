package com.nercis.isscp.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class SingleRecordStatistic {

	/**
	 * @param args
	 *            对文件进行处理，根据单个应用回收情况查下发表补全表格。
	 * @throws WriteException
	 * @throws IOException
	 * @throws BiffException
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> userAppIdList = new ArrayList<String>();
		String dfilePath = "E:\\批次病毒应用失败回收统计1.xls";
		String sfilePath = "E:\\批次病毒应用失败下发统计1.xls";
		userAppIdList = new ReadExcel().getColumnInfo(0, dfilePath, 0);
		for (int i = 0; i < userAppIdList.size(); i++) {
			String userAppId = userAppIdList.get(i);
			List<String> suserAppIdList = new ReadExcel().getColumnInfo(0, sfilePath, 0);
			int row = suserAppIdList.indexOf(userAppId) + 1;
			Workbook wb=null;
			try {
				 wb = Workbook.getWorkbook(new File(sfilePath)); // 从文件流中获取Excel工作区对象（WorkBook）
				Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet）
				List<String> rowinfoList = new ReadExcel().getRowInfo(sheet, row);
				WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
				WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
				WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
				wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN);
				wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
				wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
				wcf_left.setWrap(false); // 文字是否换行
				Workbook preWorkbook = Workbook.getWorkbook(new File(dfilePath));
				WritableWorkbook workbook = Workbook.createWorkbook(new File(dfilePath), preWorkbook);
				/** **********创建工作表************ */
				WritableSheet dsheet = workbook.getSheet(0);
				 dsheet.addCell((WritableCell) new Label(2,i+1,rowinfoList.get(2),wcf_left));
				 dsheet.addCell(new Label(3,i+1,rowinfoList.get(3),wcf_left));
				 dsheet.addCell(new Label(4,i+1,rowinfoList.get(4),wcf_left));
				 dsheet.addCell(new jxl.write.Number(5,i+1,Long.parseLong(rowinfoList.get(5)),wcf_left));
				workbook.write();
				workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(wb!=null){
					wb.close();
				}
			}
		}
	}

}
