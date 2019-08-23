package com.nercis.isscp.engine.bupt.client;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;


import com.nercis.isscp.engine.EngineOperate;
import com.nercis.isscp.engine.MissionResults;
import com.nercis.isscp.idl.AppSendMessage;
import com.nercis.isscp.idl.JobPriority;
import com.nercis.isscp.idl.PlotsType;
import com.nercis.isscp.idl.UserApp;
import com.nercis.isscp.util.ReadExcel;
import com.nercis.isscp.util.UserAppInfo;
import com.softsec.tase.common.util.StringUtils;

public class PerformanceStabilityTestClient {
	private static Properties properties = new Properties();

	private static String queue_name = null;
	private static String queue_url = null;
	private static String engine_address = null;
	private static int engine_port = 0;

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
	}

	public static void main(String[] args) {
		String filePath = "";
		int missionSize = 10;
		int beginNumber = 0;
		String missionId = "";
		try {
			missionId = autoissueMessages(filePath, missionSize, beginNumber);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(missionSize * 2 * 60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(searchMission(missionId));

	}

	/**
	 * 下发任务，指定excel的文件目录
	 * 
	 * @param filePath
	 *            指定样本excel的文件目录
	 * @param missionSize
	 *            一个批次的应用数量
	 * @param beginNumber
	 *            样本在excel表里开始的位置
	 * @throws JMSException
	 */
	public static String autoissueMessages(String filePath, int missionSize, int beginNumber) throws JMSException {
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

		List<UserAppInfo> appInfoList = new ReadExcel().getUserAppInfoList(filePath, 0);
		appInfoList = appInfoList.subList(beginNumber, beginNumber + missionSize);
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
		String time1 = sdf.format(time);
		MessageDigest messageDigest;
		String missionId = "";
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			missionId = "Mission" + StringUtils.byteArrayToHexString(messageDigest.digest(time1.getBytes()));
			issueMultiMessages(session, producer, missionId, missionSize, appInfoList);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return missionId;

	}

	/**
	 * 
	 * @param session
	 * @param producer
	 * @param missionId
	 * @param missionSize
	 * @param userAppInfos
	 * @return
	 * @throws JMSException
	 */
	private static long issueMultiMessages(Session session, MessageProducer producer, String missionId, int missionSize,
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
			userApp.setUserAppId(missionId + "@" + i);
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

		session.commit();
		// connection.close();
		return time;
	}

	public static String searchMission(String missionId) {
		TTransport transport = new TSocket(engine_address, engine_port);
		try {
			transport.open();
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 设置传输协议为TBinaryProtocol
		TProtocol protocol1 = new TBinaryProtocol(transport);
		// 调用服务的方法
		EngineOperate.Client client = new EngineOperate.Client(protocol1);

		MissionResults result = new MissionResults();
		try {
			result = client.searchMission(missionId);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.getMissionProgress();
	}
}
