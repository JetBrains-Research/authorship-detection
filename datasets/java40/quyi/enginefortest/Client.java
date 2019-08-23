/**
 * 
 */
package com.nercis.isscp.engine.bupt.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.nercis.isscp.engine.ConfigModifyService;
import com.nercis.isscp.engine.Engine;
import com.nercis.isscp.engine.EngineOperate;
import com.nercis.isscp.engine.EngineOperation;
import com.nercis.isscp.engine.bupt.impl.TSOperateImpl;
import com.nercis.isscp.idl.CheckResultStatus;
import com.nercis.isscp.idl.InvalidRequestException;
import com.nercis.isscp.idl.PlotsType;
import com.nercis.isscp.idl.Sample;
import com.nercis.isscp.idl.UnavailableException;
import com.nercis.isscp.idl.UserApp;
import com.nercis.isscp.idl.rules.Activity;
import com.nercis.isscp.idl.rules.Risk;
import com.nercis.isscp.idl.rules.Rules;
import com.nercis.isscp.ts.TSOperate;
import com.softsec.tase.common.rpc.service.notify.StatusNotifyService;

/**
 * @author luantingyuan
 * 
 */
public class Client {
	public static final String address = "localhost";

	public static void main(String[] args) throws InvalidRequestException, UnavailableException, TException {
		// 设置调用的服务地址为本地，端口为7911
		TTransport transport = new TSocket(address, 7778);
		try {
			transport.open();
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 设置传输协议为TBinaryProtocol
		TProtocol protocol = new TBinaryProtocol(transport);
		// 调用服务的方法
		TSOperate.Client client=new TSOperate.Client(protocol);
		Map<PlotsType, CheckResultStatus>plotsStatus=new HashMap<PlotsType, CheckResultStatus>();
		plotsStatus.put(PlotsType.staticType, CheckResultStatus.executed);
		client.updateUserAppStatus("SingleTest", "SingleTest1", plotsStatus, null);
	}
}
