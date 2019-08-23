package com.nercis.isscp.engine.bupt.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import android.R.integer;

import com.nercis.isscp.util.ReadExcel;
import com.nercis.isscp.util.UserAppInfo;

public class WorkLoadTestClient {
	
	private static Properties properties = new Properties();

	private static String queue_name = null;
	private static String queue_url = null;

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
		queue_name = properties.getProperty("activeMq.messagequeue.name", "waitingCheckQueueTest");
		queue_url = properties.getProperty("activeMq.queue.url", "tcp://localhost:61616");
	}


	public static void main(String[] args) throws JMSException, InterruptedException {
		// TODO Auto-generated method stub
		for(int i=0;i<9;i++){
			workLoadTest("E:\\个人\\论文实验\\样本数据新测试.xls", "workloadTestcase1511138" + i, 99, i);
		    Thread.sleep(10*60*1000);
		}
	}
	public static void workLoadTest(String filePath,String missionId,int missionSize,int count ) throws JMSException{
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
		List<UserAppInfo> appInfoList = new ReadExcel().getUserAppInfoList(filePath, 0).subList(count*missionSize, (count+1)*missionSize);
        long time= PerformanceTestClient.issueMultiMessages(session, producer, missionId, missionSize, count, appInfoList);
		System.out.println("missionId:"+missionId+",time:"+time);
	}

}
