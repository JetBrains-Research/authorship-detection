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
package org.chacha;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.chacha.model.Account;
import org.chacha.util.JsonPojoMapper;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.undercouch.bson4jackson.BsonFactory;
import de.undercouch.bson4jackson.BsonGenerator;

public class PerformanceBsonJson {
	private Account src=new Account();
	private Account dst=new Account();
	
	@Before
	public void setUP(){
		src.setUserName("chachacha");
		src.setPasswd("dddddddd");
	}
	
	/**
	 * json转换性能测试
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void testJson() throws JsonGenerationException, JsonMappingException, IOException{
		long begin=System.currentTimeMillis();
		for(int i=0;i<1000000;i++){
			String jsonString=JsonPojoMapper.toJson(src, true);
			dst=(Account)JsonPojoMapper.fromJson(jsonString, Account.class);
		}
		long end=System.currentTimeMillis();
		System.out.println("json="+(end-begin));
	}
	/**
	 * bson解析性能测试
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void testBson() throws JsonGenerationException, JsonMappingException, IOException{
		BsonFactory fac=new BsonFactory();
		fac.enable(BsonGenerator.Feature.ENABLE_STREAMING);
		ObjectMapper mapper=new ObjectMapper(fac);
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		long begin=System.currentTimeMillis();
		for(int i=0;i<1000000;i++){
			mapper.writeValue(baos, src);
			ByteArrayInputStream bais=new ByteArrayInputStream(baos.toByteArray());
			dst=mapper.readValue(bais, Account.class);
			baos.reset();
		}
		long end=System.currentTimeMillis();
		System.out.println("bson="+(end-begin));
	}
}
