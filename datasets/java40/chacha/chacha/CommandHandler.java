package org.chacha.cmdproxy;

import java.io.IOException;

import org.chacha.context.LoginContext;
import org.chacha.context.LoginContextImpl;
import org.chacha.entity.AuthRequest;
import org.chacha.entity.AuthResponse;
import org.chacha.entity.Request;
import org.chacha.entity.Response;
import org.chacha.entity.ResponseCode;
import org.chacha.log.LogPrn;
import org.chacha.util.JsonPojoMapper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class CommandHandler {
	private static LogPrn log=new LogPrn(CommandHandler.class);
	
	/**
	 * ��Ч������
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	private static String handleInvalidCommand() throws JsonMappingException, JsonGenerationException, IOException{
		Response response=new Response();
		response.setResult(ResponseCode.ERROR_COMMAND);
		return JsonPojoMapper.toJson(response, true);	
	}
	
	/**
	 * ��������
	 * @param requestJson
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws IOException
	 */
	public static String handle(String requestJson) throws JsonMappingException, JsonParseException, IOException{
		Request request=(Request) JsonPojoMapper.fromJson(requestJson, Request.class);
		String opt=request.getOpt();
		if(opt.equalsIgnoreCase(AuthRequest.CMD_AUTH)){
			return handleLoginPass(requestJson);
		}else if(opt.equalsIgnoreCase(AuthRequest.CMD_TOKEN)){
			return (requestJson);
		}else if(opt.equalsIgnoreCase(AuthRequest.CMD_LOGOUT)){
			return (requestJson);
		}else{
			return  handleInvalidCommand();
		}
	}
	
	/**
	 * ������֤
	 * @param reqJson
	 * @return ������֤����json��ʽ�ִ�
	 * @throws JsonGenerationException 
	 * @throws IOException 
	 * @throws JsonParseException 
	 * @throws JsonMappingException 
	 */
	@SuppressWarnings("finally")
	private static String handleLoginPass(String requestJson) throws JsonMappingException, JsonGenerationException, IOException{
		AuthResponse response=new AuthResponse();
		LoginContext context=new LoginContextImpl();
		try{
			AuthRequest request=(AuthRequest)JsonPojoMapper.fromJson(requestJson, Request.class);
			response=context.loginPass(request);		
		}catch(Exception e){
			response.setResult(ResponseCode.EXCEPTION);
			log.error("CommandHandler handleLoginPass exception",e);
		}finally{
			return JsonPojoMapper.toJson(response, true);
		}
	}
	
	/**
	 * ������֤
	 * @param requestJson
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	@SuppressWarnings({ "finally", "unused" })
	private static String handleLoginToken(String requestJson) throws JsonMappingException, JsonGenerationException, IOException{
		AuthResponse response=new AuthResponse();
		LoginContext context=new LoginContextImpl();
		try{
			AuthRequest request=(AuthRequest)JsonPojoMapper.fromJson(requestJson, Request.class);
			response=context.loginToken(request);
		}catch(Exception e){
			response.setResult(ResponseCode.EXCEPTION);
			log.error("CommandHandler handleLoginToken exception",e);
		}finally{
			return JsonPojoMapper.toJson(response, true);
		}
	}
	
	/**
	 * ����ע��
	 * @param requestJosn
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	@SuppressWarnings({ "finally", "unused" })
	private static String handleLogout(String requestJosn) throws JsonMappingException, JsonGenerationException, IOException{
		AuthResponse response=new AuthResponse();
		LoginContext context=new LoginContextImpl();
		try{
			AuthRequest request=(AuthRequest)JsonPojoMapper.fromJson(requestJosn, Request.class);
			response=context.loginOut(request);
		}catch(Exception e){
			response.setResult(ResponseCode.EXCEPTION);
			log.error("CommandHandler handleLogout exception",e);
		}finally{
			return JsonPojoMapper.toJson(response, true);
		}
	}
}
