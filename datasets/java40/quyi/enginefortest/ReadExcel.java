package com.nercis.isscp.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nercis.isscp.idl.PlotsType;
import com.sun.imageio.plugins.wbmp.WBMPImageReader;

import android.R.integer;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {
	public static void main(String[] args) {
		// System.out.println(getColumnInfo(0,
		// "E:\\实验室\\项目\\信工所\\6月6日张妍测试文档\\信工所应用样本列表.xls", 0));
		// System.out.println(getRowInfo(1,
		// "E:\\实验室\\项目\\信工所\\6月6日张妍测试文档\\信工所应用样本列表.xls", 0));
		// System.out.println(getUserAppInfo(1,
		// "E:\\实验室\\项目\\信工所\\6月6日张妍测试文档\\信工所应用样本列表.xls", 0));
		// System.out.println(getColumnSize(1,"E:\\实验室\\项目\\信工所\\6月6日张妍测试文档\\信工所应用样本列表.xls",
		// 0));
		new Thread(new SingleRecordInfoConsumer("E:\\实验室\\项目\\信工所\\6月6日张妍测试文档\\单个应用下发统计.xls")).start();
		System.out.println("开启线程写文件");
		SingleRecordInfo singleRecordInfo = new SingleRecordInfo();
		singleRecordInfo.setAppName("百度电视云.apk");
		singleRecordInfo.setAppSize("7411941");
		singleRecordInfo.setMd5("480e65eeb554bb22d96156f8aa1ce82b");
		singleRecordInfo.setMissionId("SingleTest");
		singleRecordInfo.setUserAppId("SingleTest1");
		singleRecordInfo.setPlotsType(PlotsType.virusType);
		singleRecordInfo.setStartedTime(System.currentTimeMillis());
		 singleRecordInfo.setFinishedTime(System.currentTimeMillis());
		singleRecordInfo.setAppCheckResult("success");
		SingleRecordInfoQueue.getInstance().addToSingleRecordInfoQueue(singleRecordInfo);
		//new ExportExcel().exportToSingleExcel("E:\\实验室\\项目\\信工所\\6月6日张妍测试文档\\单个应用统计.xls", singleRecordInfo);
	}

	// get column info
	public List<String> getColumnInfo(int column, String fileName, int sheetNum) {
		List<String> columninfoList = new ArrayList<String>();
		File file = new File(fileName); // 创建文件对象
		if (!file.exists()) {
			return null;
		}
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
			Sheet sheet = wb.getSheet(sheetNum); // 从工作区中取得页（Sheet）

			for (int i = 1; i < sheet.getRows(); i++) { // 循环打印Excel表中的内容
				Cell cell = sheet.getCell(column, i);
				columninfoList.add(cell.getContents());
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (wb != null) {
				wb.close();
			}
		}
		return columninfoList;

	}
	// get column info
		public Set<String> getColumnInfoSet(int column, String fileName, int sheetNum) {
			Set<String> columninfoSet = new HashSet<String>();
			File file = new File(fileName); // 创建文件对象
			if (!file.exists()) {
				return null;
			}
			Workbook wb = null;
			try {
				wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
				Sheet sheet = wb.getSheet(sheetNum); // 从工作区中取得页（Sheet）

				for (int i = 1; i < sheet.getRows(); i++) { // 循环打印Excel表中的内容
					Cell cell = sheet.getCell(column, i);
					columninfoSet.add(cell.getContents());
				}
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (wb != null) {
					wb.close();
				}
			}
			return columninfoSet;

		}

	// get row info by row num
	public List<String> getRowInfo(Sheet sheet, int row) {
		List<String> rowinfoList = new ArrayList<String>();
//		File file = new File(fileName); // 创建文件对象
//		if (!file.exists()) {
//			return null;
//		}
//		Workbook wb = null;
//		try {
//			wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
//			Sheet sheet = wb.getSheet(sheetNum); // 从工作区中取得页（Sheet）

			for (int i = 0; i < sheet.getColumns(); i++) { // 循环打印Excel表中的内容
				Cell cell = sheet.getCell(i, row);
				rowinfoList.add(cell.getContents());
			}
//		} catch (BiffException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (wb != null) {
//				wb.close();
//			}
//		}
		return rowinfoList;
	}

	public int getRowSize(int column, String fileName, int sheetNum) {
		File file = new File(fileName); // 创建文件对象
		if (!file.exists()) {
			return 0;
		}
		int size = 0;
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
			Sheet sheet = wb.getSheet(sheetNum); // 从工作区中取得页（Sheet）
			size = sheet.getRows();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (wb != null) {
				wb.close();
			}
		}
		return size;

	}

	// get UserAppInfo by List
	public UserAppInfo getUserAppInfo(Sheet sheet, int row, String fileName, int sheetNum) {
		List<String> rowinfoList = getRowInfo(sheet, row);
		if (rowinfoList.size() < 4) {
			return null;
		}
		UserAppInfo userAppInfo = new UserAppInfo(rowinfoList.get(0), rowinfoList.get(1), rowinfoList.get(2), rowinfoList.get(3));
		return userAppInfo;
	}
	
	//get userAppinfo by filename
	public List<UserAppInfo> getUserAppInfoList(String fileName,int sheetNum) {
		File file = new File(fileName); // 创建文件对象
		if (!file.exists()) {
			return null;
		}
		Workbook wb = null;
		Sheet sheet = null;
		try {
			wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
			sheet = wb.getSheet(sheetNum); // 从工作区中取得页（Sheet）
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		int rowSize=getRowSize(0, fileName, sheetNum);
		List<UserAppInfo>userAppInfos=new ArrayList<UserAppInfo>();
		for(int i=1;i<rowSize;i++){
			userAppInfos.add(getUserAppInfo(sheet, i, fileName, sheetNum));
		}
		if (wb != null) {
			wb.close();
		}
		return userAppInfos;
		
	}
	
	

}
