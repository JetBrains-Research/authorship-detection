/*
 * Copyright 2012 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.chacha.client;

import org.chacha.client.command.Response;
import org.chacha.client.command.account.AddAccountRequest;
import org.chacha.client.command.account.DelAccountRequest;
import org.chacha.client.command.account.ModifyAccountRequest;
import org.chacha.client.command.account.QueryAccountRequest;
import org.chacha.client.command.account.QueryAccountResponse;
import org.chacha.client.command.auth.LoginRequest;
import org.chacha.client.command.auth.LoginResponse;
import org.chacha.client.command.auth.TokenLogout;
import org.chacha.client.command.auth.TokenRequest;
import org.chacha.exception.ChachaException;

public interface CommandService {
	/**
	 * 用户密码认证
	 * @param request  密码认证的请求消息
	 * @return			认证响应消息
	 * @throws ChachaException
	 */
	public LoginResponse loginPassword(LoginRequest request) throws ChachaException;
	/**
	 * 令牌认证
	 * @param request  令牌认证请求
	 * @return         令牌认证回应消息
	 * @throws ChachaException
	 */
	public LoginResponse loginToken(TokenRequest request) throws ChachaException;
	
	/**
	 * 令牌注销
	 * @param request	令牌注销请求消息
	 * @return			令牌注销回应消息
	 * @throws ChachaException
	 */
	public LoginResponse logout(TokenLogout request) throws ChachaException;
	/**
	 * 新增帐号
	 * @param request  新增帐号请求消息，可新增批量帐号
	 * @return		   新增帐号回应消息
	 * @throws ChachaException
	 */
	public Response addAccount(AddAccountRequest request) throws ChachaException;
	/**
	 * 修改帐号
	 * @param request 修改帐号请求，可批量修改帐号	
	 * @return	      修改帐号回应
	 * @throws ChachaException
	 */
	public Response modifyAccount(ModifyAccountRequest request) throws ChachaException;
	/**
	 * 删除帐号
	 * @param request 删除帐号请求，可以批量删除帐号
	 * @return			删除帐号回应
	 * @throws ChachaException
	 */
	public Response delAccount(DelAccountRequest request) throws ChachaException;
	/**
	 * 查询帐号
	 * @param request 查询帐号请求
	 * @return		查询帐号回应
	 * @throws ChachaException
	 */
	public QueryAccountResponse queryAccount(QueryAccountRequest request) throws ChachaException;
	
}
