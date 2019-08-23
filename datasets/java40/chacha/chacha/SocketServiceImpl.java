package org.chacha.client;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.chacha.client.command.auth.LoginRequest;
import org.chacha.client.command.auth.LoginResponse;
import org.chacha.client.command.auth.TokenLogout;
import org.chacha.client.command.auth.TokenRequest;
import org.chacha.exception.ChachaException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class SocketServiceImpl implements CommandService {
    private InetSocketAddress[] hosts;
    private Sender sender;
    
    
	public SocketServiceImpl(InetSocketAddress[] hosts) {
		this.hosts = hosts;
		this.sender = new SocketSender(this.hosts);
	}

	public SocketServiceImpl(InetSocketAddress host) {
		this(new InetSocketAddress[]{host});
	}

	public LoginResponse loginPassword(LoginRequest request)
			throws ChachaException {
		LoginResponse r=new LoginResponse();
		try {
			String res=request.toJson();
			String response=sender.syncRequest(res);
			r.fromJson(response);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}

	public LoginResponse loginToken(TokenRequest request)
			throws ChachaException {
		LoginResponse r=new LoginResponse();
		try {
			String res=request.toJson();
			String response=sender.syncRequest(res);
			r.fromJson(response);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}

	public LoginResponse logout(TokenLogout request) throws ChachaException {
		LoginResponse r=new LoginResponse();
		try {
			String res=request.toJson();
			String response=sender.syncRequest(res);
			r.fromJson(response);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}

}
