package com.ctbt.beidou.base.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class EncryptUtil {

	protected static Logger logger = Logger.getLogger(EncryptUtil.class);

	/**
	 * MD5 加密
	 * @param plainText
	 * @return
	 */
	public static String Md5(String plainText) {
		if(plainText == null) return null;

		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for(int offset = 0; offset < b.length; offset++){
				i = b[offset];
				if(i < 0) i += 256;
				if(i < 16) buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			//			System.out.println("result 32: " + buf.toString());// 32位的加密
			//
			//			System.out.println("result 16: " + buf.toString().substring(8, 24));// 16位的加密

			return buf.toString();
		}catch (NoSuchAlgorithmException e){
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * 3DES 加密
	 * @param data
	 * @param key	长度不能超过24
	 * @return
	 * @throws Exception
	 */
	public static byte[] DESedeEncrypt(byte[] data, byte[] key) throws Exception {
		SecretKey deskey = new SecretKeySpec(key, "DESede");
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		return cipher.doFinal(data);
	}

	/**
	 * 3DES 解密
	 * @param data
	 * @param key	长度不能超过24
	 * @return
	 * @throws Exception
	 */
	public static byte[] DESedeDecrypt(byte[] data, byte[] key) throws Exception {
		SecretKey deskey = new SecretKeySpec(key, "DESede");
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		return cipher.doFinal(data);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//		EncryptUtil.Md5(null);

		String txt = "12345678901234567890abcd";
		String key = "111111112222222233333333";
		byte[] plainData = txt.substring(0, 24).getBytes();

		try{
			byte[] data = EncryptUtil.DESedeEncrypt(plainData, key.getBytes());
			System.out.println("自定义加密后长度：" + data.length + ", 内容：" + new String(data));

			byte[] data2 = EncryptUtil.DESedeDecrypt(data, key.getBytes());
			System.out.println("自定义解密后长度："+data2.length+", 内容："+new String(data2));

		}catch (Exception e){
			e.printStackTrace();
		}

		System.exit(0);
	}
}
