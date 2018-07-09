package com.ctbt.beidou.base.utils;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.ctbt.beidou.base.CommValue;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncodeUtil {
	
	protected static Logger logger = Logger.getLogger(EncodeUtil.class);
	
	public static String getBASE64(String s) {
		if(s == null) return null;

		try{
			BASE64Encoder encoder = new BASE64Encoder();
			String str = encoder.encode(s.getBytes("utf-8"));
			char[] chr = str.toCharArray();
			StringBuffer strBuf = new StringBuffer();
			for(int k = 0; k < chr.length; k++){
				if(chr[k] != '\r' && chr[k] != '\n' && chr[k] != '\t'){
					strBuf.append(chr[k]);
				}
			}
			// str.replaceAll("", "\r");
			// str.replaceAll("", "\n");
			return strBuf.toString();
		}catch (UnsupportedEncodingException e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	// 将 BASE64 编码的字符串 s 进行解码
	public static String getFromBASE64(String s) {
		if(s == null) return null;

		BASE64Decoder decoder = new BASE64Decoder();
		try{
			byte[] b = decoder.decodeBuffer(s);
			return new String(b, CommValue.CHARSET);
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	     * unicode 编码 转换成 中文
	     * 
	     * @param in
	     * @param off
	     * @param len
	     * @param convtBuf
	     * @return
	     */
	public static String toChinese(String unicodeStr) {
		if(null == unicodeStr || "".equals(unicodeStr.trim())) return "";

		char[] in = unicodeStr.toCharArray();
		int off = 0;
		int len = in.length;
		char[] convtBuf = new char[len * 2];

		if(convtBuf.length < len){
			int newLen = len * 2;
			if(newLen < 0){
				newLen = Integer.MAX_VALUE;
			}
			convtBuf = new char[newLen];
		}
		char aChar;
		char[] out = convtBuf;
		int outLen = 0;
		int end = off + len;

		while (off < end){
			aChar = in[off++];
			if(aChar == '\\'){
				aChar = in[off++];
				if(aChar == 'u'){
					// Read the xxxx
					int value = 0;
					for(int i = 0; i < 4; i++){
						aChar = in[off++];
						switch(aChar){
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								value = (value << 4) + aChar - '0';
								break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
								value = (value << 4) + 10 + aChar - 'a';
								break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
								value = (value << 4) + 10 + aChar - 'A';
								break;
							default:
								throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
						}
					}
					out[outLen++] = (char) value;
				}else{
					if(aChar == 't')
						aChar = '\t';
					else if(aChar == 'r')
						aChar = '\r';
					else if(aChar == 'n')
						aChar = '\n';
					else if(aChar == 'f') aChar = '\f';
					out[outLen++] = aChar;
				}
			}else{
				out[outLen++] = (char) aChar;
			}
		}
		return new String(out, 0, outLen);
	}

	/**
	     * 中文 转换成 unicode 编码
	     * 
	     * @param in
	     * @param off
	     * @param len
	     * @param convtBuf
	     * @return
	     */
	public static String toUnicode(String cnStr) {
		if(null == cnStr || "".equals(cnStr.trim())) return "";

		boolean escapeSpace = false;
		int len = cnStr.length();
		int bufLen = len * 2;
		if(bufLen < 0){
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for(int x = 0; x < len; x++){
			char aChar = cnStr.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if((aChar > 61) && (aChar < 127)){
				if(aChar == '\\'){
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch(aChar){
				case ' ':
					// if (x == 0 || escapeSpace) outBuffer.append('\\');
					outBuffer.append(' ');
					break;
				case '\t':
					outBuffer.append('\t');
					break;
				case '\n':
					outBuffer.append('\n');
					break;
				case '\r':
					outBuffer.append('\r');
					break;
				case '\f':
					outBuffer.append('\f');
					break;
				case '=': // Fall through
				case ':': // Fall through
				case '#': // Fall through
				case '!':
					// outBuffer.append('\\');
					outBuffer.append(aChar);
					break;
				default:
					if((aChar < 0x0020) || (aChar > 0x007e)){
						outBuffer.append('\\');
						outBuffer.append('u');
						outBuffer.append(toHex((aChar >> 12) & 0xF));
						outBuffer.append(toHex((aChar >> 8) & 0xF));
						outBuffer.append(toHex((aChar >> 4) & 0xF));
						outBuffer.append(toHex(aChar & 0xF));
					}else{
						outBuffer.append(aChar);
					}
			}
		}
		return outBuffer.toString();
	}

	/**
	     * Convert a nibble to a hex character
	     * 
	     * @param nibble
	     *                the nibble to convert.
	     */
	private static char toHex(int nibble) {
		return hexDigit[(nibble & 0xF)];
	}

	/** A table of hex digits */
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

}
