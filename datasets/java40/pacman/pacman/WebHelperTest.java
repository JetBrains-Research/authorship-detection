/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: WebHelperTest.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.test
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月28日 下午5:24:44
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.test;

import org.junit.Test;
import org.springframework.util.Assert;

import com.yeshj.pacman.utils.FileHelper;
import com.yeshj.pacman.utils.WebHelper;

/**
 * TODO
 * @Class: WebHelperTest
 * @author: zhangxinyu
 * @date: 2015年1月28日 下午5:24:44
 */
public class WebHelperTest {

	@Test
	public void testDownload() {
		
		String url = "http://f1.ct.7.hjfile.cn/ppt/201501a/x150115202849405ebd02e3b65b8f4c43/board-14.jpg"; 
		WebHelper.download(url, "d:\\board-14.jpg");
		
		Assert.isTrue(FileHelper.exists("d:\\board-14.jpg"));
	}
}
