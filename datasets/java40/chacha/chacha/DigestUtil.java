package org.chacha.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.codec.digest.DigestUtils;

public class DigestUtil {
	public final static String MD5="MD5";
	public final static String NONE="NONE";
	public final static String SHA_1="SHA_1";
	public final static String SHA_256="SHA_256";
	public final static String SHA_512="SHA_512";
	public final static String SHA_384="SHA_384";
	
	/**
	 * 文件加密
	 * @param filename
	 * @param algorithm
	 */
	public static void digestFile(String filename,String algorithm){
		byte[] b=new byte[1024*4];
		int len=0;
		FileOutputStream fos = null;
		FileInputStream fis=null;
		try {
			MessageDigest md=MessageDigest.getInstance(algorithm);
			fis=new FileInputStream(filename);
			while((len=fis.read(b))!=-1){
				md.update(b, 0, len);
			}
			byte[] digest=md.digest();
			OutputStream encodeStream=new Base64OutputStream(fos);
			encodeStream.write(digest);
			encodeStream.flush();
			encodeStream.close();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				if(fis!=null)
					fis.close();
			}catch(Exception ignored){
				
			}
			try{
				if(fos!=null)
					fis.close();
			}catch(Exception ignored){
				
			}
		}
	}
	
	/**
	 * 密码加密算法
	 */
    public static String digestString(String password,String alg) throws NoSuchAlgorithmException {  
        String newPass;  
        if (alg == null || MD5.equals(alg)) {  
            newPass = DigestUtils.md5Hex(password);  
        } else if (NONE.equals(alg)) {  
            newPass = password;  
        } else if(SHA_1.equals(alg)){
        	newPass=DigestUtils.sha1Hex(password);
        } else if (SHA_256.equals(alg)) {  
            newPass = DigestUtils.sha256Hex(password);  
        } else if (SHA_384.equals(alg)) {  
            newPass = DigestUtils.sha384Hex(password);  
        } else if (SHA_512.equals(alg)) {  
            newPass = DigestUtils.sha512Hex(password);  
        } else {  
            newPass = DigestUtils.shaHex(password);  
        }  
        return newPass;  
    }  

    /** 
     * 加密密码算法，默认的加密算法是SHA-256  
     *  
     * @param password 
     *            未加密的密码 
     * @return String 加密后的密码,64位字符串
     */  
    public static String digestPassword(String password) {  
        try {  
            if (password != null && !"".equals(password)) {  
                return digestString(password, SHA_256);  
            } else  
                return null;  
        } catch (NoSuchAlgorithmException nsae) {  
            throw new RuntimeException("Security error: " + nsae);  
        }  
    }  
    
    /** 
     * 判断密码是不是相等，默认的加密算法是SHA-256 
     *  
     * @param beforePwd 
     *            要判断的密码 
     * @param afterPwd 
     *            加密后的数据库密码 
     * @return Boolean true 密码相等 
     */  
    public static boolean isPasswordEnable(String beforePwd, String afterPwd) {  
        if (beforePwd != null && !"".equals(beforePwd)) {  
            String password = digestPassword(beforePwd);  
            return afterPwd.equals(password);  
        } else  
            return false;  
    }  
}
