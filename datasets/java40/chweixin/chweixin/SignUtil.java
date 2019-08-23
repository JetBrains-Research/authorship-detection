package ipower.wechat.utils;

import ipower.utils.MD5Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 校验工具类。
 * @author yangyong.
 * @since 2014-02-21.
 * */
public final class SignUtil {
	/**
	 * 验证签名。
	 * @param signature
	 * 	加密签名。
	 * @param token
	 * 	令牌。
	 * @param timestamp
	 *  时间戳
	 * @param nonce
	 * 	随机数。
	 * @return 验证通过返回true,否则返回false。
	 * */
	public synchronized static boolean checkSignature(String signature,String token,String timestamp,String nonce){
		//将token,timestamp,nonce三个参数进行字典排序。
		String[] source = new String[]{token,timestamp,nonce};
		Arrays.sort(source);
		
		StringBuilder content = new StringBuilder();
		for(int i = 0; i < source.length; i++){
			content.append(source[i]);
		}
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] bytes = digest.digest(content.toString().getBytes());
			String result = MD5Util.ConvertHex(bytes);
			content = null;
			// 将sha1加密后的字符串可与signature对比.
			return (result != null && !result.trim().isEmpty()) ? result.equalsIgnoreCase(signature): false;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}