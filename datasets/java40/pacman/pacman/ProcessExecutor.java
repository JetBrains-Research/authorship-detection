/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: ProcessExecutor.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.utils
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月4日 下午11:48:45
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.yeshj.pacman.log.ILog;
import com.yeshj.pacman.log.LogFactory;

/**
 * @ClassName: ProcessExecutor
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月4日 下午11:48:45
 */
public class ProcessExecutor {

	private final static ILog logger = LogFactory.getLog(ProcessExecutor.class);
	public static final int SUCCESS = 0;

    /**
     * execute the external program.
     * 
     * @Title: work
     * @param reader
     * @param cmdFormat
     * @param args
     * @return
     * @throws Exception
     * @return: boolean
     */
    public boolean execute(List<String> outList, 
    					     String cmdFormat, 
    						 Object... args) 
    		throws Exception {

    	boolean result = false;
   	
    	String cmdline = String.format(cmdFormat, args);
        Process process = Runtime.getRuntime().exec(cmdline);
        /*
    	final List<String> output = new ArrayList<String>();
    	final List<String> errput = new ArrayList<String>();        
        final InputStream is = process.getInputStream();
        final InputStream es = process.getErrorStream();
        
        new Thread() {
        	
        	public void run() {
        		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        		String line = null;
        		try {
					while((line = reader.readLine()) != null) {
						output.add(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
        	};
        }.start();

        new Thread() {
        	
        	public void run() {
        		BufferedReader reader = new BufferedReader(new InputStreamReader(es));
        		String line = null;
        		try {
					while((line = reader.readLine()) != null) {
						errput.add(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
        	};
        }.start();
        */
        result = process.waitFor() == SUCCESS;
        /*
        if (outList != null) {
        	
	        if (result) //execute successfully!
	        	outList.addAll(output);
	        else
	        	outList.addAll(errput);
        }
        */
        return result;
    }
    
    public boolean runShell(String shellCmd) {
    	
    	boolean result = false;
    	
    	StringBuffer sb = new StringBuffer();
    	try {
	    	Process proc = Runtime.getRuntime().exec(shellCmd);
	    	BufferedReader br = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
	    	String line = null;
	    	result = proc.waitFor() == 0;
	    	while ((line = br.readLine()) != null)
	    	{
	    		sb.append(line + "\n");
	    	}
	    	logger.info(sb.toString());
	    	
    	} catch (Exception e) {
    		logger.error("runShell error!", e);
    		result = false;
    	}
    	
    	return result;
    }
}
