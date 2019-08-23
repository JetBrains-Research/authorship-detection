package com.nercis.isscp.engine.bupt.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import android.R.integer;
import brut.androlib.res.decoder.ResAttrDecoder;

import com.nercis.isscp.engine.EngineOperate;
import com.nercis.isscp.idl.AppSendMessage;
import com.nercis.isscp.idl.CheckResultStatus;
import com.nercis.isscp.idl.InvalidRequestException;
import com.nercis.isscp.idl.JobPriority;
import com.nercis.isscp.idl.PlotsType;
import com.nercis.isscp.idl.Sample;
import com.nercis.isscp.idl.UnavailableException;
import com.nercis.isscp.idl.UserApp;
import com.nercis.isscp.util.ExportExcel;
import com.nercis.isscp.util.ReadExcel;
import com.nercis.isscp.util.SearchRecordInfoConsumer;
import com.nercis.isscp.util.SingleRecordInfo;
import com.nercis.isscp.util.SingleRecordInfoConsumer;
import com.nercis.isscp.util.SingleRecordInfoQueue;
import com.nercis.isscp.util.UserAppInfo;

public class PerformanceTestClient {

	private static Properties properties = new Properties();

	private static String queue_name = null;
	private static String queue_url = null;
	private static String engine_address = null;
	private static int engine_port = 0;
	private static int engine_notify_port = 0;
	private static String taskmanager_address = null;
	private static int taskmanager_submit_port = 0;
	private static int taskmanager_result_port = 0;
	private static String static_nodemanager_address = null;
	private static String virus_nodemanager_address = null;
	private static int nodemanager_submit_port = 0;
	private static int nodemanager_result_port = 0;

	static {
		try {
			InputStream input = TestClientForNercis.class.getClassLoader().getResourceAsStream("client.properties");
			if (input == null) {
				input = TestClientForNercis.class.getResourceAsStream("client.properties");
			}
			properties.load(input);
		} catch (IOException e) {
			throw new RuntimeException("Load config file failed", e);
		}
		queue_name = properties.getProperty("activeMq.messagequeue.name", "waitingCheckQueue");
		queue_url = properties.getProperty("activeMq.queue.url", "tcp://localhost:61616");
		engine_address = properties.getProperty("engine.address", "localhost");
		engine_port = Integer.valueOf(properties.getProperty("engine.port", "7911"));
		engine_notify_port = Integer.valueOf(properties.getProperty("engine.notify.port", "7910"));
		taskmanager_address = properties.getProperty("taskmanager.listener.domain ", "localhost");
		taskmanager_submit_port = Integer.valueOf(properties.getProperty("taskmanager.node.port", "6000"));
		taskmanager_result_port = Integer.valueOf(properties.getProperty("taskmanager.result.port", "6020"));
		static_nodemanager_address = properties.getProperty("static.nodemanager.listener.domain", "localhost");
		virus_nodemanager_address = properties.getProperty("virus.nodemanager.listener.domain", "localhost");
		nodemanager_submit_port = Integer.valueOf(properties.getProperty("nodemanager.context.port", "7000"));
		nodemanager_result_port = Integer.valueOf(properties.getProperty("program.tracker.service.port", "7020"));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new Thread(new SingleRecordInfoConsumer("D:\\批量下发统计.xls")).start();
		//autoissueMessages("C:\\Users\\Administrator\\Desktop\\20160817信工所\\带毒-100\\带毒100.xls", 5, 5*60000, 100, 0);
		autoissueMessages("C:\\Users\\Administrator\\Desktop\\20160817信工所\\无毒\\正常表格.xls", 10, 5*60000, 100, 0);

	}

	public static void autoissueMessages(String filePath, int missionSize, long waitTime, int issueAppSize, int sheetNum) {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session = null;
		Destination destination;
		MessageProducer producer = null;
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, queue_url);
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(queue_name);
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<UserAppInfo> appInfoList = new ReadExcel().getUserAppInfoList(filePath, sheetNum);
		appInfoList = appInfoList.subList(0, issueAppSize);
		for (int missionId = 0; missionId < appInfoList.size() / missionSize; missionId++) {
			String realMissionId = "MultiTest20160817" + String.valueOf(missionSize) + "@" + String.valueOf(missionId);
			System.out.println(realMissionId);
			List<UserAppInfo> userAppInfos = new ArrayList<UserAppInfo>();
			if ((missionId + 1) * missionSize < appInfoList.size()) {
				userAppInfos = appInfoList.subList(missionId * missionSize, (missionId + 1) * missionSize);
			} else {
				userAppInfos = appInfoList.subList(missionId * missionSize, appInfoList.size());
				//userAppInfos = appInfoList.subList(0, appInfoList.size());
			}
			try {
				long time = issueMultiMessages(session, producer, realMissionId, missionSize, missionId * missionSize, userAppInfos);

				
				// todo 写文件
				for (int i = 0; i < userAppInfos.size(); i++) {
					UserAppInfo userAppInfo = userAppInfos.get(i);
					SingleRecordInfo singleRecordInfo = new SingleRecordInfo();
					singleRecordInfo.setAppName(userAppInfo.getAppName());
					singleRecordInfo.setAppSize(userAppInfo.getSize());
					singleRecordInfo.setMd5(userAppInfo.getMd5());
					singleRecordInfo.setStartedTime(time);
					singleRecordInfo.setMissionId(realMissionId);
					singleRecordInfo.setPlotsType(PlotsType.virusType);
					singleRecordInfo.setUserAppId("MultiTest" + missionSize + (missionId * missionSize + i));				
					SingleRecordInfoQueue.getInstance().addToSingleRecordInfoQueue(singleRecordInfo);
					
					
					
//					SingleRecordInfo singleRecordInfo1 = new SingleRecordInfo();
//					singleRecordInfo1.setAppName(userAppInfo.getAppName());
//					singleRecordInfo1.setAppSize(userAppInfo.getSize());
//					singleRecordInfo1.setMd5(userAppInfo.getMd5());
//					singleRecordInfo1.setStartedTime(time);
//					singleRecordInfo1.setMissionId(realMissionId);
//					singleRecordInfo1.setPlotsType(PlotsType.virusType);
//					singleRecordInfo1.setUserAppId("MultiTest" + missionSize + (missionId * missionSize + i));
//					SingleRecordInfoQueue.getInstance().addToSingleRecordInfoQueue(singleRecordInfo1);
				}
				

			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		try {
//			Thread.sleep(60 * 60000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	
	public static void autoissueMessages_test(String filePath, int missionSize, long waitTime, int issueAppSize, int sheetNum) {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session = null;
		Destination destination;
		MessageProducer producer = null;
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, queue_url);
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(queue_name);
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<UserAppInfo> appInfoList = new ReadExcel().getUserAppInfoList(filePath, sheetNum);
		appInfoList = appInfoList.subList(0, issueAppSize);
		for (int missionId = 0; missionId <= appInfoList.size() / missionSize; missionId++) {
			String realMissionId = "Multi" + String.valueOf(missionSize) + "@" + String.valueOf(missionId);
			System.out.println(realMissionId);
			List<UserAppInfo> userAppInfos = new ArrayList<UserAppInfo>();
			if ((missionId + 1) * missionSize < appInfoList.size()) {
				userAppInfos = appInfoList.subList(missionId * missionSize, (missionId + 1) * missionSize);
			} else {
				userAppInfos = appInfoList.subList(missionId * missionSize, appInfoList.size());
				//userAppInfos = appInfoList.subList(0, appInfoList.size());
			}
		
				// todo 写文件
				for (int i = 0; i < userAppInfos.size(); i++) {
					UserAppInfo userAppInfo = userAppInfos.get(i);
					SingleRecordInfo singleRecordInfo = new SingleRecordInfo();
					singleRecordInfo.setAppName(userAppInfo.getAppName());
					singleRecordInfo.setAppSize(userAppInfo.getSize());
					singleRecordInfo.setMd5(userAppInfo.getMd5());
					singleRecordInfo.setStartedTime(0);
					singleRecordInfo.setMissionId(realMissionId);
					singleRecordInfo.setPlotsType(PlotsType.virusType);
					singleRecordInfo.setUserAppId("MultiTest" + missionSize + (missionId * missionSize + i));				
					SingleRecordInfoQueue.getInstance().addToSingleRecordInfoQueue(singleRecordInfo);
				
				}
				

			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	 static long issueMultiMessages(Session session, MessageProducer producer, String missionId, int missionSize, int count,
			List<UserAppInfo> userAppInfos) throws JMSException {
		ObjectMessage message = session.createObjectMessage();
		AppSendMessage appSendMessage = new AppSendMessage();
		appSendMessage.setMissionId(missionId);
		appSendMessage.setJobPriority(JobPriority.MEDIUM);
		List<PlotsType> plots = new ArrayList<PlotsType>();
    	//plots.add(PlotsType.staticType);
		plots.add(PlotsType.virusType);
		appSendMessage.setPlots(plots);
		appSendMessage.setRules(null);
		appSendMessage.setTaskInfo(null);
		List<UserApp> userApps = new ArrayList<UserApp>();
		for (int i = 0; i < userAppInfos.size(); i++) {
			UserAppInfo userAppInfo = userAppInfos.get(i);
			UserApp userApp = new UserApp();
			userApp.setUserAppId("MultiTest" + missionId + (count + i));
			userApp.setAppName(userAppInfo.getAppName());
			userApp.setCategory("123");
			userApp.setStoreName("1234");
			userApp.setCreateTime("123");
			userApp.setAppFileMd5(userAppInfo.getMd5());
			userApp.setVersion("1.0");
			userApp.setUpdateTime("2013-12-12 12:12:12");// yyyy-MM-dd HH:mm:ss
			userApp.setSize(userAppInfo.getSize());
			userApp.setDescription("hello");
			userApp.setAppFilePath(userAppInfo.getAppPath());
			userApps.add(userApp);
		}
		appSendMessage.setUserApps(userApps);
		message.setObject(appSendMessage);
		long time = System.currentTimeMillis();
		producer.send(message);
		// TODO record app being sent

		session.commit();
		// connection.close();
		 
//		 TTransport transport = new TSocket("192.168.2.91", 7911);
//			try {
//				transport.open();
//			} catch (TTransportException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//设置传输协议为TBinaryProtocol
//			TProtocol protocol = new TBinaryProtocol(transport);
//	        //调用服务的方法
//			EngineOperate.Client client = new EngineOperate.Client(protocol);
//			List<PlotsType> plots =new ArrayList<PlotsType>();
//			plots.add(com.nercis.isscp.idl.PlotsType.staticType);
//			List<UserApp> userApps = new ArrayList<UserApp>();
//			for (int i = 0; i < userAppInfos.size(); i++) {
//				UserAppInfo userAppInfo = userAppInfos.get(i);
//				UserApp userApp = new UserApp();
//				userApp.setUserAppId("MultiTest" + missionId + (count + i));
//				userApp.setAppName(userAppInfo.getAppName());
//				userApp.setCategory("123");
//				userApp.setStoreName("1234");
//				userApp.setCreateTime("123");
//				userApp.setAppFileMd5(userAppInfo.getMd5());
//				userApp.setVersion("1.0");
//				userApp.setUpdateTime("2013-12-12 12:12:12");// yyyy-MM-dd HH:mm:ss
//				userApp.setSize(userAppInfo.getSize());
//				userApp.setDescription("hello");
//				userApp.setAppFilePath(userAppInfo.getAppPath());
//				userApps.add(userApp);
//			}
//			try {
//				client.issuedMessage(missionId, userApps, null, plots);
//
//			} catch (InvalidRequestException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (UnavailableException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (TException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			transport.close();
//			long time = System.currentTimeMillis();
		return time;
	}

	private static void issueSingleMessage(Session session, MessageProducer producer, String missionid, String userAppId, String appPath,
			String appName, String appMd5, String appSize) throws JMSException {
		ObjectMessage message = session.createObjectMessage();
		AppSendMessage appSendMessage = new AppSendMessage();
		appSendMessage.setMissionId(missionid);
		appSendMessage.setJobPriority(JobPriority.MEDIUM);
		List<PlotsType> plots = new ArrayList<PlotsType>();
		//plots.add(PlotsType.staticType);
		plots.add(PlotsType.staticType);
		appSendMessage.setPlots(plots);
		appSendMessage.setRules(null);
		appSendMessage.setTaskInfo(null);
		UserApp userApp = new UserApp();
		userApp.setUserAppId(userAppId);
		userApp.setAppName(appName);
		userApp.setCategory("123");
		userApp.setStoreName("1234");
		userApp.setCreateTime("123");
		userApp.setAppFileMd5(appMd5);
		userApp.setVersion("1.0");
		userApp.setUpdateTime("2013-12-12 12:12:12");// yyyy-MM-dd HH:mm:ss
		userApp.setSize(appSize);
		userApp.setDescription("hello");
		userApp.setAppFilePath(appPath);
		List<UserApp> userApps = new ArrayList<UserApp>();
		userApps.add(userApp);
		appSendMessage.setUserApps(userApps);
		message.setObject(appSendMessage);
		producer.send(message);
		// TODO record app being sent

		session.commit();
		// connection.close();
	}

	public static void autoIssueSingleMessage() {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session = null;
		Destination destination;
		MessageProducer producer = null;
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, queue_url);
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(queue_name);
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReadExcel readExcel = new ReadExcel();
		// ExportExcel exportExcel = new ExportExcel();
		String fileName = "D:\\信工所应用样本列表.xls";
		// String fileName1 = "E:\\实验室\\项目\\信工所\\6月6日张妍测试文档\\单个应用统计.xls";
		int rowsize = readExcel.getRowSize(0, fileName, 0);

		File file = new File(fileName); // 创建文件对象
		if (!file.exists()) {
			return;
		}
		Workbook wb = null;
		Sheet sheet = null;
		try {
			wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
			sheet = wb.getSheet(0); // 从工作区中取得页（Sheet）
		} catch (BiffException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int row = 1; row < rowsize; row++) {
			UserAppInfo userAppInfo = readExcel.getUserAppInfo(sheet, row, fileName, 1);
			try {
				long time1 = System.currentTimeMillis();
				issueSingleMessage(session, producer, "SingleTest11", "SingleTest11" + row, userAppInfo.getAppPath(), userAppInfo.getAppName(),
						userAppInfo.getMd5(), userAppInfo.getSize());
				/*SingleRecordInfo singleRecordInfo = new SingleRecordInfo();
				singleRecordInfo.setAppName(userAppInfo.getAppName());
				singleRecordInfo.setAppSize(userAppInfo.getSize());
				singleRecordInfo.setMd5(userAppInfo.getMd5());
				singleRecordInfo.setMissionId("SingleTest");
				singleRecordInfo.setUserAppId("SingleTest" + row);
				singleRecordInfo.setStartedTime(time1);
				singleRecordInfo.setPlotsType(PlotsType.staticType);
				SingleRecordInfoQueue.getInstance().addToSingleRecordInfoQueue(singleRecordInfo);*/
				// exportExcel.exportToSingleExcel(fileName1, singleRecordInfo);
				SingleRecordInfo singleRecordInfo1 = new SingleRecordInfo();
				singleRecordInfo1.setAppName(userAppInfo.getAppName());
				singleRecordInfo1.setAppSize(userAppInfo.getSize());
				singleRecordInfo1.setMd5(userAppInfo.getMd5());
				singleRecordInfo1.setMissionId("SingleTest11");
				singleRecordInfo1.setUserAppId("SingleTest11" + row);
				singleRecordInfo1.setStartedTime(time1);
				singleRecordInfo1.setPlotsType(PlotsType.staticType);
				//singleRecordInfo1.setPlotsType(PlotsType.virusType);
				// exportExcel.exportToSingleExcel(fileName1, singleRecordInfo);
				SingleRecordInfoQueue.getInstance().addToSingleRecordInfoQueue(singleRecordInfo1);
				// todo 记录相关信息并写到excel中
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(5 * 60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (wb != null) {
			wb.close();
		}
	}

	public static void autoSearchApp(String filePath) {
		List<String> userAppIdList = new ArrayList<String>();
		Set<String> userAppIdSet = new HashSet<String>();
		userAppIdSet = new ReadExcel().getColumnInfoSet(0, filePath, 0);
		Iterator<String> iterator = userAppIdSet.iterator();
		while (iterator.hasNext()) {
			String userAppId = iterator.next();
			userAppIdList.add(userAppId);
		}
		searchApp(userAppIdList, 10, 1 * 60000, 200);
		searchApp(userAppIdList, 20, 2 * 60000, 300);
		searchApp(userAppIdList, 50, 5 * 60000, 300);
		searchApp(userAppIdList, 100, 10 * 60000, 400);

	}

	public static void searchApp(List<String> userAppIdList1, int missionSize, long waitTime, int count) {
		List<String> userAppIdList = new ArrayList<String>();
		userAppIdList = userAppIdList1.subList(0, count);
		String missionId = "SingleTest";
		for (int appCount = 0; appCount <= userAppIdList.size() / missionSize; appCount++) {
			for (int userAppCount = 0; userAppCount < missionSize && appCount * missionSize + userAppCount < userAppIdList.size(); userAppCount++) {
				String userAppId = userAppIdList.get(appCount * missionSize + userAppCount);
				String number = missionSize + "#" + appCount + "@" + userAppCount;
				SearchAppThread searchAppThread = new SearchAppThread(engine_address, engine_port, missionId, userAppId, number, missionSize);
				searchAppThread.run();
			}
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void autoSearchMission(String filePath) {
		List<String> userAppIdList = new ArrayList<String>();
		Set<String> userAppIdSet = new HashSet<String>();
		userAppIdSet = new ReadExcel().getColumnInfoSet(1, filePath, 0);
		Iterator<String> iterator = userAppIdSet.iterator();
		while (iterator.hasNext()) {
			String userAppId = iterator.next();
			userAppIdList.add(userAppId);
		}
		searchMission(userAppIdList, 1, 2 * 60000, 45);

	}

	public static void searchMission(List<String> missionIdList1, int missionSize, long waitTime, int count) {
		List<String> missionIdList = missionIdList1.subList(0, count);
		for (int missionCount = 0; missionCount <= missionIdList.size() / missionSize; missionCount++) {
			for (int missionIdCount = 0; missionIdCount < missionSize && missionCount * missionSize + missionIdCount < missionIdList.size(); missionIdCount++) {
				String missionId = missionIdList.get(missionCount * missionSize + missionIdCount);
				for (int i = 0; i < 10; i++) {
					SearchMissionThread searchAppThread = new SearchMissionThread(engine_address, engine_port, missionId, missionId);
					searchAppThread.run();
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// searchEngine
	public static void searchEngine(int searchCount, long waitTime) {
		// TODO set node id
		String nodeId = "192.168.116.156:7000";
		for (int i = 0; i < searchCount; i++) {
			SearchEngine searchAppThread = new SearchEngine(engine_address, engine_port, nodeId, searchCount + "@" + i + "");
			searchAppThread.run();
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// searchAllEngine
	public static void searchAllEngine(int searchCount, long waitTime) {
		for (int i = 0; i < searchCount; i++) {
			SearchAllEngineThread searchAllEngineThread = new SearchAllEngineThread(engine_address, engine_port,searchCount+"@" +i + "");
			searchAllEngineThread.run();
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
