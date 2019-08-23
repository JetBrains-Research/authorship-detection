package com.nercis.isscp.engine.bupt.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
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
import org.junit.BeforeClass;
import org.junit.Test;

import com.nercis.isscp.engine.EngineOperate;
import com.nercis.isscp.idl.AppSendMessage;
import com.nercis.isscp.idl.JobPriority;
import com.nercis.isscp.idl.PlotsType;
import com.nercis.isscp.idl.UserApp;
import com.nercis.isscp.util.TaskClientRpcUtils;
import com.softsec.tase.common.domain.result.AndroidStaticResults;
import com.softsec.tase.common.domain.result.AndroidStaticSimpleResults;
import com.softsec.tase.common.rpc.domain.app.AppStatus;
import com.softsec.tase.common.rpc.domain.app.AppType;
import com.softsec.tase.common.rpc.domain.container.BundleType;
import com.softsec.tase.common.rpc.domain.container.Context;
import com.softsec.tase.common.rpc.domain.job.ContextParameter;
import com.softsec.tase.common.rpc.domain.job.JobDistributionMode;
import com.softsec.tase.common.rpc.domain.job.JobExecutionMode;
import com.softsec.tase.common.rpc.domain.job.JobLifecycle;
import com.softsec.tase.common.rpc.domain.job.JobOperationRequirement;
import com.softsec.tase.common.rpc.domain.job.JobParameter;
import com.softsec.tase.common.rpc.domain.job.JobPhase;
import com.softsec.tase.common.rpc.domain.job.JobResourceRequirement;
import com.softsec.tase.common.rpc.domain.job.JobReturnMode;
import com.softsec.tase.common.rpc.domain.node.ClusterType;
import com.softsec.tase.common.rpc.domain.node.NodeType;
import com.softsec.tase.common.rpc.exception.InvalidRequestException;
import com.softsec.tase.common.rpc.exception.TimeoutException;
import com.softsec.tase.common.rpc.exception.UnavailableException;
import com.softsec.tase.common.rpc.service.node.ProgramTrackerService;
import com.softsec.tase.common.rpc.service.node.TaskService;
import com.softsec.tase.common.rpc.service.notify.StatusNotifyService;
import com.softsec.tase.common.rpc.service.task.NodeTrackerService;
import com.softsec.tase.common.rpc.service.task.TaskClientService;

public class TestClientForNercis {
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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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

	@Test
	public void issuedMessageTest001() throws Exception {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session;
		Destination destination;
		MessageProducer producer;
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, queue_url);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue(queue_name);
		producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		ObjectMessage message = session.createObjectMessage();
		AppSendMessage appSendMessage = new AppSendMessage();
		appSendMessage.setMissionId("issuedMessageTest001");
		appSendMessage.setJobPriority(JobPriority.MEDIUM);
		List<PlotsType> plots = new ArrayList<PlotsType>();
		plots.add(PlotsType.staticType);
		appSendMessage.setPlots(plots);
		appSendMessage.setRules(null);
		appSendMessage.setTaskInfo(null);
		UserApp userApp = new UserApp();
		userApp.setUserAppId("issuedMessage001");
		userApp.setAppName("test.apk");
		userApp.setCategory("123");
		userApp.setStoreName("1234");
		userApp.setCreateTime("123");
		userApp.setAppFileMd5("1b6066c5a107ebdbf1e5c9e4b31891bc");
		userApp.setVersion("1.0");
		userApp.setUpdateTime("2013-12-12 12:12:12");// yyyy-MM-dd HH:mm:ss
		userApp.setSource("123");
		userApp.setDescription("hello");
		userApp.setAppFilePath("test/test.apk");
		List<UserApp> userApps = new ArrayList<UserApp>();
		userApps.add(userApp);
		appSendMessage.setUserApps(userApps);
		message.setObject(appSendMessage);
		System.out.println("发送消息：" + "ActiveMq 发送的消息" + appSendMessage.toString());
		producer.send(message);
		session.commit();
		connection.close();
	}

	@Test
	public void issuedMessageTest002() throws Exception {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session;
		Destination destination;
		MessageProducer producer;
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, queue_url);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue(queue_name);
		producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		ObjectMessage message = session.createObjectMessage();
		AppSendMessage appSendMessage = new AppSendMessage();
		appSendMessage.setMissionId("issuedMessageTest002");
		appSendMessage.setJobPriority(JobPriority.MEDIUM);
		List<PlotsType> plots = new ArrayList<PlotsType>();
		plots.add(PlotsType.staticType);
		appSendMessage.setPlots(plots);
		appSendMessage.setRules(null);
		appSendMessage.setTaskInfo(null);
		UserApp userApp = new UserApp();
		userApp.setUserAppId("issuedMessageTest002");
		userApp.setAppName("test.apk");
		userApp.setCategory("123");
		userApp.setStoreName("1234");
		userApp.setCreateTime("123");
		userApp.setAppFileMd5("1b6066c5a107ebdbf1e5c9e4b31891bc");
		userApp.setVersion("1.0");
		userApp.setUpdateTime("2013-12-12 12:12:12");// yyyy-MM-dd HH:mm:ss
		userApp.setSource("123");
		userApp.setDescription("hello");
		userApp.setAppFilePath("test");
		List<UserApp> userApps = new ArrayList<UserApp>();
		userApps.add(userApp);
		appSendMessage.setUserApps(userApps);
		message.setObject(appSendMessage);
		System.out.println("发送消息：" + "ActiveMq 发送的消息" + appSendMessage.toString());
		producer.send(message);
		session.commit();
		connection.close();
	}

	@Test
	public void searchAppTest001() {
		try {
			TTransport transport = new TSocket(engine_address, engine_port);
			transport.open();
			TProtocol protocol1 = new TBinaryProtocol(transport);
			EngineOperate.Client client = new EngineOperate.Client(protocol1);
			System.out.println(client.searchApp("test", "testSearchApp001"));
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void searchAppTest002() {
		try {
			TTransport transport = new TSocket(engine_address, engine_port);
			transport.open();
			TProtocol protocol1 = new TBinaryProtocol(transport);
			EngineOperate.Client client = new EngineOperate.Client(protocol1);
			System.out.println(client.searchApp("null", "null"));
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void searchMissionTest001() {
		try {
			TTransport transport = new TSocket(engine_address, engine_port);
			transport.open();
			// 设置传输协议为TBinaryProtocol
			TProtocol protocol1 = new TBinaryProtocol(transport);
			// 调用服务的方法
			EngineOperate.Client client = new EngineOperate.Client(protocol1);
			System.out.println(client.searchMission("test"));
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void searchMissionTest002() {
		try {
			TTransport transport = new TSocket(engine_address, engine_port);
			transport.open();
			// 设置传输协议为TBinaryProtocol
			TProtocol protocol1 = new TBinaryProtocol(transport);
			// 调用服务的方法
			EngineOperate.Client client = new EngineOperate.Client(protocol1);
			System.out.println(client.searchMission("null"));
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void searchEngTest001() {
		try {
			TTransport transport = new TSocket(engine_address, engine_port);
			transport.open();
			TProtocol protocol1 = new TBinaryProtocol(transport);
			EngineOperate.Client client = new EngineOperate.Client(protocol1);
			System.out.println(client.searchEng("testSearchEng"));
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void searchEngTest002() {
		try {
			TTransport transport = new TSocket(engine_address, engine_port);
			transport.open();
			TProtocol protocol1 = new TBinaryProtocol(transport);
			EngineOperate.Client client = new EngineOperate.Client(protocol1);
			System.out.println(client.searchEng("null"));
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void appStatusNotifyTest001() {
		try {
			TTransport transport = new TSocket(engine_address, engine_notify_port);
			transport.open();
			TProtocol protocol1 = new TBinaryProtocol(transport);
			StatusNotifyService.Client client = new StatusNotifyService.Client(protocol1);
			System.out.println(client.appStatusNotify(110000640100000000L, 1000L, JobPhase.ON_STATIC, AppStatus.ISSUED, null, null, null));
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void appStatusNotifyTest002() {
		try {
			TTransport transport = new TSocket(engine_address, engine_notify_port);
			transport.open();
			TProtocol protocol1 = new TBinaryProtocol(transport);
			StatusNotifyService.Client client = new StatusNotifyService.Client(protocol1);
			System.out.println(client.appStatusNotify(0, 1000L, JobPhase.ON_STATIC, AppStatus.ISSUED, null, null, null));
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSubmitJob001() throws TimeoutException, TException {
		List<JobOperationRequirement> jobOpList = new ArrayList<JobOperationRequirement>();
		JobOperationRequirement taskOp2 = new JobOperationRequirement();
		taskOp2.setJobLifecycle(JobLifecycle.ANALYSIS);
		taskOp2.setJobPhase(JobPhase.PROCESS);
		taskOp2.setJobExecutionMode(JobExecutionMode.EXCLUSIVE);
		taskOp2.setJobReturnMode(JobReturnMode.PASSIVE);
		taskOp2.setTimeout(180000);
		jobOpList.add(taskOp2);

		List<JobParameter> jobParameterList = new ArrayList<JobParameter>();
		JobParameter taskParam1 = new JobParameter();
		taskParam1.setJobPhase(JobPhase.PROCESS);
		taskParam1.setAppId(1000L);
		List<ContextParameter> contextParamList1 = new ArrayList<ContextParameter>();
		ContextParameter parameter1 = new ContextParameter();
		parameter1.setSequenceNum(1);
		parameter1.setContent("test/test.apk");
		parameter1.setNeedDownload(true);
		contextParamList1.add(parameter1);
		ContextParameter parameter2 = new ContextParameter();
		parameter2.setSequenceNum(2);
		parameter2.setContent(String.valueOf(taskParam1.getAppId()));
		parameter2.setNeedDownload(false);
		contextParamList1.add(parameter2);
		taskParam1.setContextParameterList(contextParamList1);
		jobParameterList.add(taskParam1);

		List<JobPhase> jobPhaseList = new ArrayList<JobPhase>();
		jobPhaseList.add(JobPhase.PROCESS);
		List<JobResourceRequirement> jobRescList = new ArrayList<JobResourceRequirement>();
		JobResourceRequirement jobResc2 = new JobResourceRequirement();
		jobResc2.setJobLifecycle(JobLifecycle.ANALYSIS);
		jobResc2.setJobPhase(JobPhase.PROCESS);
		jobResc2.setClusterType(ClusterType.DEDICATED);
		jobResc2.setNodeType(NodeType.INTERNET_ACCESSIBLE);
		jobRescList.add(jobResc2);
		TaskClientService.Client taskClient = null;
		long jobId = 0L;
		try {
			taskClient = TaskClientRpcUtils.getReceiver(taskmanager_address, taskmanager_submit_port, 5000, 5);
		} catch (TTransportException e) {
			e.printStackTrace();
		}

		try {
			jobId = taskClient.submitJobForMission(0, AppType.APK, JobLifecycle.ANALYSIS, jobPhaseList, JobDistributionMode.PARALLEL,
					com.softsec.tase.common.rpc.domain.job.JobPriority.MEDIUM, jobOpList, jobRescList, jobParameterList, 5000L, "test");
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jobId);
	}

	@Test
	public void testSubmitJob002() {
		List<JobOperationRequirement> jobOpList = new ArrayList<JobOperationRequirement>();
		JobOperationRequirement taskOp2 = new JobOperationRequirement();
		taskOp2.setJobLifecycle(JobLifecycle.ANALYSIS);
		taskOp2.setJobPhase(JobPhase.PROCESS);
		taskOp2.setJobExecutionMode(JobExecutionMode.EXCLUSIVE);
		taskOp2.setJobReturnMode(JobReturnMode.PASSIVE);
		taskOp2.setTimeout(180000);
		jobOpList.add(taskOp2);

		List<JobParameter> jobParameterList = new ArrayList<JobParameter>();
		JobParameter taskParam1 = new JobParameter();
		taskParam1.setJobPhase(JobPhase.PROCESS);
		taskParam1.setAppId(1101097200000001L);
		List<ContextParameter> contextParamList1 = new ArrayList<ContextParameter>();
		ContextParameter parameter1 = new ContextParameter();
		parameter1.setSequenceNum(1);
		parameter1.setContent("analysis/apk/00/45/0045e7a851a8adcdd6590a944b057b1c.apk");
		parameter1.setNeedDownload(true);
		contextParamList1.add(parameter1);
		ContextParameter parameter2 = new ContextParameter();
		parameter2.setSequenceNum(2);
		parameter2.setContent(String.valueOf(taskParam1.getAppId()));
		parameter2.setNeedDownload(false);
		contextParamList1.add(parameter2);
		taskParam1.setContextParameterList(contextParamList1);
		jobParameterList.add(taskParam1);

		List<JobPhase> jobPhaseList = new ArrayList<JobPhase>();
		jobPhaseList.add(JobPhase.PROCESS);

		List<JobResourceRequirement> jobRescList = new ArrayList<JobResourceRequirement>();
		JobResourceRequirement jobResc2 = new JobResourceRequirement();
		jobResc2.setJobLifecycle(JobLifecycle.ANALYSIS);
		jobResc2.setJobPhase(JobPhase.PROCESS);
		jobResc2.setClusterType(ClusterType.DEDICATED);
		jobResc2.setNodeType(NodeType.INTERNET_ACCESSIBLE);
		jobRescList.add(jobResc2);
		TaskClientService.Client taskClient = null;
		long jobId = 0L;
		try {
			taskClient = TaskClientRpcUtils.getReceiver(taskmanager_address, taskmanager_submit_port, 5000, 5);
		} catch (TTransportException e) {
			e.printStackTrace();
		}

		try {
			jobId = taskClient.submitJobForMission(0, AppType.APK, JobLifecycle.ANALYSIS, jobPhaseList, JobDistributionMode.PARALLEL,
					com.softsec.tase.common.rpc.domain.job.JobPriority.MEDIUM, jobOpList, jobRescList, jobParameterList, 5000L, null);
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jobId);

	}

	@Test
	public void testSubmitResult001() {
		AndroidStaticSimpleResults appstaticresults = new AndroidStaticSimpleResults();
		appstaticresults.setApkName("test");
		appstaticresults.setApkDescription("test");
		appstaticresults.setDetectDate(100L);
		appstaticresults.setMD5_Apk("test");
		ByteBuffer content = ByteBuffer.wrap(getBytes(appstaticresults));
		TTransport transport = new TSocket(taskmanager_address, taskmanager_result_port);
		try {
			transport.open();
		} catch (TTransportException e) {
			// LOGGER.error("Failed to notify app : " + e.getMessage(), e);
		}
		TProtocol protocol = new TBinaryProtocol(transport);
		NodeTrackerService.Client client = new NodeTrackerService.Client(protocol);
		try {
			int retvalue = client.submitResult(AppType.APK, JobLifecycle.ANALYSIS, JobPhase.ON_STATIC, content, "ce4d06f1db496088df12f8b8a4f5bbe0",
					110000640100000101L, "1201000000000001");
			System.out.println(retvalue);
		} catch (InvalidRequestException e) {
			e.printStackTrace();
		} catch (UnavailableException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			transport.close();
		}
	}

	@Test
	public void testSubmitResult002() {
		AndroidStaticSimpleResults appstaticresults = new AndroidStaticSimpleResults();
		appstaticresults.setApkName("test");
		appstaticresults.setApkDescription("test");
		appstaticresults.setDetectDate(100L);
		appstaticresults.setMD5_Apk("test");
		ByteBuffer content = ByteBuffer.wrap(getBytes(appstaticresults));
		TTransport transport = new TSocket(taskmanager_address, taskmanager_result_port);
		try {
			transport.open();
		} catch (TTransportException e) {
			// LOGGER.error("Failed to notify app : " + e.getMessage(), e);
		}
		TProtocol protocol = new TBinaryProtocol(transport);
		NodeTrackerService.Client client = new NodeTrackerService.Client(protocol);
		try {
			int retvalue = client.submitResult(AppType.APK, JobLifecycle.ANALYSIS, JobPhase.INITIALIZE, content, "123",
					110000640100000101L, "1201000000000001");
			System.out.println(retvalue);
		} catch (InvalidRequestException e) {
			e.printStackTrace();
		} catch (UnavailableException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			transport.close();
		}
	}

	@Test
	public void testSubmitContext001() {
		TTransport transport = new TSocket(static_nodemanager_address, nodemanager_submit_port);
		try {
			transport.open();
		} catch (TTransportException e) {

		}
		TProtocol protocol = new TBinaryProtocol(transport);
		TaskService.Client client = new TaskService.Client(protocol);
		Context context = new Context();
		context.setJobPhase(JobPhase.ON_STATIC);
		// FIXME test data here
		context.setBundleType(BundleType.ZIP);
		context.setEnvVariables("env=jar");
		context.setExecutableMd5("test");
		context.setExecutableName("test");
		context.setExecutablePath("text");
		// context.setProgramId(1100000301L);
		context.setProgramName("test");
		context.setScriptMd5("test");
		context.setScriptName("test");
		context.setScriptPath("test");
		context.setPriority(com.softsec.tase.common.rpc.domain.job.JobPriority.HIGH);
		context.setPriorityIsSet(true);
		context.setTaskId(110000640100000000L);
		context.setProgramId(1);
		context.setBundleTypeIsSet(true);
		context.setJobExecutionMode(JobExecutionMode.EXCLUSIVE);
		context.setJobReturnMode(JobReturnMode.PASSIVE);
		context.setTimeout(120000L);
		
		JobParameter parameter = new JobParameter();
		parameter.setAppId(1000L);
		parameter.setJobPhase(JobPhase.ON_STATIC);
		List<ContextParameter> contextParameterList = new ArrayList<ContextParameter>();
		ContextParameter contextParameter = new ContextParameter();
		contextParameter.setSequenceNum(1);
		contextParameter.setContent("test");
		contextParameterList.add(contextParameter);
		parameter.setContextParameterList(contextParameterList);
		context.setParameter(parameter);
		try {
			System.out.println(client.submitContext(context));
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.getInputProtocol().getTransport().close();
			}
		}
	}

	@Test
	public void testSubmitContext002() {
		TTransport transport = new TSocket(static_nodemanager_address, nodemanager_submit_port);
		try {
			transport.open();
		} catch (TTransportException e) {
		}
		TProtocol protocol = new TBinaryProtocol(transport);
		TaskService.Client client = new TaskService.Client(protocol);
		Context context = new Context();
		context.setJobPhase(JobPhase.ON_STATIC);
		// FIXME test data here
		context.setBundleType(BundleType.ZIP);
		context.setEnvVariables("env=jar");
		context.setExecutableMd5("test");
		context.setExecutableName("test");
		context.setExecutablePath("text");
		// context.setProgramId(1100000301L);
		context.setProgramName("test");
		context.setScriptMd5("test");
		context.setScriptName("test");
		context.setScriptPath("test");
		context.setPriority(com.softsec.tase.common.rpc.domain.job.JobPriority.HIGH);
		// context.setTaskId(11111111111L);
		context.setProgramId(1);
		context.setJobExecutionMode(JobExecutionMode.EXCLUSIVE);
		context.setJobReturnMode(JobReturnMode.PASSIVE);
		context.setTimeout(120000L);
		try {
			System.out.println(client.submitContext(context));
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.getInputProtocol().getTransport().close();
			}
		}
	}

	@Test
	public void testTransferResult001() {
		TTransport transport = new TSocket(static_nodemanager_address, nodemanager_result_port);
		try {
			transport.open();
		} catch (TTransportException e) {
		}
		TProtocol protocol = new TBinaryProtocol(transport);
		ProgramTrackerService.Client client = new ProgramTrackerService.Client(protocol);
		try {
			AndroidStaticResults appstaticresults = new AndroidStaticResults();
			appstaticresults.setApkName("test.apk");
			appstaticresults.setResultReportPath("D:\\0000test.xml");
			appstaticresults.setApkDescription("123");
			appstaticresults.setDetectDate(100L);
			appstaticresults.setMD5_Apk("test");
			ByteBuffer content = ByteBuffer.wrap(getBytes(appstaticresults));
			int result = client.transferResult(AppType.APK, JobLifecycle.ANALYSIS, JobPhase.ON_STATIC, content, 110000640100000000L, "1000");

			System.out.println(result);
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.getInputProtocol().getTransport().close();
			}
		}
	}

	@Test
	public void testTransferResult002() {
		TTransport transport = new TSocket(static_nodemanager_address, nodemanager_result_port);
		try {
			transport.open();
		} catch (TTransportException e) {
		}
		TProtocol protocol = new TBinaryProtocol(transport);
		ProgramTrackerService.Client client = new ProgramTrackerService.Client(protocol);
		try {
			AndroidStaticResults appstaticresults = new AndroidStaticResults();
			appstaticresults.setApkName("test.apk");
			appstaticresults.setResultReportPath("D:\\0000test.xml");
			appstaticresults.setApkDescription("123");
			appstaticresults.setDetectDate(100L);
			appstaticresults.setMD5_Apk("test");
			ByteBuffer content = ByteBuffer.wrap(getBytes(appstaticresults));
			int result = client.transferResult(null, JobLifecycle.ANALYSIS, JobPhase.ON_STATIC, content, 110000640100000000L,
					"1000");

			System.out.println(result);
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.getInputProtocol().getTransport().close();
			}
		}
	}

	public static byte[] getBytes(Serializable obj) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		byte[] bytes = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
			bytes = baos.toByteArray();
			baos.close();
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}
}
