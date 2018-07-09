package com.ctbt.beidou.base.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {

	public static String trim(String str) {
		if(str == null){
			return "";
		}

		return str.trim();
	}

	public static String trim(String str, String def) {
		if(str == null || "".equals(str)){
			return def;
		}

		return str.trim();
	}

	public static String filterSqlValue(String sql) {
		if(sql == null) return "";

		String newtext = sql;

		newtext = newtext.replaceAll("\"", "“");
		newtext = newtext.replaceAll("\'", "‘");
		newtext = newtext.replaceAll("=", "");
		newtext = newtext.replaceAll("%", "％");
		newtext = newtext.replaceAll("@", "＠");
		newtext = newtext.replaceAll(",", "");
		newtext = newtext.replaceAll(" ", "");

		return newtext;
	}

	/**
	 * 过滤 ajax、json 的特殊字符
	 * @param text
	 * @return
	 */
	public static String filter(String text) {
		if(text == null) return "";

		text = text.replaceAll("\"", "<KYSC01>");
		text = text.replaceAll("\'", "<KYSC02>");
		text = text.replaceAll("\\{", "<KYSC03>");
		text = text.replaceAll("\\}", "<KYSC04>");
		text = text.replaceAll("\\&", "<KYSC05>");
		text = text.replaceAll("%", "<KYSC06>");
		text = text.replaceAll("\\$", "<KYSC07>");
		text = text.replaceAll("@", "<KYSC08>");
		text = text.replaceAll(",", "<KYSC09>");
		text = text.replaceAll("\\\\", "<KYSC10>");
		text = text.replaceAll(";", "<KYSC11>");
		text = text.replaceAll("=", "<KYSC12>");
		text = text.replaceAll("\\+", "<KYSC13>");

		return text;
	}

	/**
	 * 还原 ajax、json 特殊字符
	 * @param text
	 * @return
	 */
	public static String unfilter(String text) {
		if(text == null) return "";

		text = text.replaceAll("<KYSC01>", "\"");
		text = text.replaceAll("<KYSC02>", "\'");
		text = text.replaceAll("<KYSC03>", "\\{");
		text = text.replaceAll("<KYSC04>", "\\}");
		text = text.replaceAll("<KYSC05>", "\\&");
		text = text.replaceAll("<KYSC06>", "%");
		text = text.replaceAll("<KYSC07>", "\\$");
		text = text.replaceAll("<KYSC08>", "@");
		text = text.replaceAll("<KYSC09>", ",");
		text = text.replaceAll("<KYSC10>", "\\\\");
		text = text.replaceAll("<KYSC11>", ";");
		text = text.replaceAll("<KYSC12>", "=");
		text = text.replaceAll("<KYSC13>", "\\+");

		return text;
	}

	/**
	 * 用于页面上不同内容的字符形式显示
	 * 
	 * @param obj
	 * @return
	 */
	public static String webStr(Object obj) {
		return StrUtil.webStr(obj, "");
	}

	public static String webStr(Object obj, String defStr) {
		if(obj == null) return defStr;
		String str = "";
		if(obj instanceof String){
			return StrUtil.html2Ubb(String.valueOf(obj));
		}else if(obj instanceof Date){
			return DateUtil.date2String((Date) obj);
		}else if(obj instanceof Integer){
			return String.valueOf((Integer) obj);
		}else if(obj instanceof Float){
			return String.valueOf((Float) obj);
		}else if(obj instanceof Double){
			return String.valueOf((Double) obj);
		}else if(obj instanceof BigDecimal){
			return String.valueOf((BigDecimal) obj);
		}

		return str;
	}

	/**
	     * 页面上使用，替换 换行符 为空格，处理成内容连续的字符串，长度控制在 maxLen
	     * 
	     * @param str
	     * @param maxLen
	     * @return
	     */
	public static String limitLen(String str, Integer maxLen) {
		if(str == null) return "&nbsp;";
		
		int total = str.length();
		if(total > maxLen){
			float len = 0f;
			StringBuffer strBuf = new StringBuffer();
			char[] chars = str.toCharArray();
			char c = 0;
			for(int k = 0; k < total && len < maxLen; k++){
				c = chars[k];
				strBuf.append(c);
				if(c < 127){
					len += 0.5;
				}else{
					len += 1;
				}
			}
			
			str = strBuf.toString()+ "...";
		}

		str = str.replaceAll("\n", "&nbsp;");
		return str;
	}

	public static String getString(String str) {
		String s = str;
		try{
			byte temp[] = s.getBytes("ISO-8859-1");
			s = new String(temp);
			return s;
		}catch (Exception e){
			return s;
		}
	}

	public static String html2Ubb(String html) {
		if(html == null) return "&nbsp;";

		html = html.replaceAll(">", "&gt;");
		html = html.replaceAll("<", "&lt;");
		html = html.replaceAll("\n", "<BR>");
		html = html.replaceAll(" ", "&nbsp;");
		html = html.replaceAll("\"", "&quot;");
		html = html.replaceAll("\'", "��");

		return html;
	}

	public static String ubb2Html(String bbcode) {
		if(bbcode == null) return "";
		
		bbcode = bbcode.replaceAll("&gt;", ">");
		bbcode = bbcode.replaceAll("&lt;", "<");
		bbcode = bbcode.replaceAll("<BR>", "\n");
		bbcode = bbcode.replaceAll("&nbsp;", " ");
		bbcode = bbcode.replaceAll("&quot;", "\"");
		bbcode = bbcode.replaceAll("��", "\'");
		
		return bbcode;
	}

	/**
	 * ckeditor 在初始化时，会将原本数据库中 &lt; &gt;等字符，自动转化为< > ，
	 * 这将原本是文本的内容 转换成了html，形成 html注入漏洞
	 * 
	 * 解决办法是 在初始化前，将数据库中的内容，所有的 “&” 替换为 “&amp;” 这样初始化后，正好是原本的内容 
	 * 
	 * @param htmlCode
	 * @return
	 */
	public static String ckeditorFilter(String htmlCode) {
		if(htmlCode == null) return "";
		
		htmlCode = htmlCode.replaceAll("&", "&amp;");
		
		return htmlCode;
	}
	
	/**
	 * 把整数num转换成length长度的字符串，空位用0补；
	 * 
	 * @param num
	 * @param length
	 * @return
	 */
	public static String num2LenStrWith0(int num, int length) {
		String str = String.valueOf(num);
		if(length > str.length()){
			if(num >= 0 && num < 10){
				str = StrUtil.getLengthSign("0", length - 1) + str;
			}else if(num > 9 && num < 100){
				str = StrUtil.getLengthSign("0", length - 2) + str;
			}else if(num > 99 && num < 1000){
				str = StrUtil.getLengthSign("0", length - 3) + str;
			}else if(num > 999 && num < 10000){
				str = StrUtil.getLengthSign("0", length - 4) + str;
			}else if(num > 9999 && num < 100000){
				str = StrUtil.getLengthSign("0", length - 5) + str;
			}else if(num > 99999 && num < 1000000){
				str = StrUtil.getLengthSign("0", length - 6) + str;
			}else if(num > 999999 && num < 10000000){
				str = StrUtil.getLengthSign("0", length - 7) + str;
			}else if(num > 9999999 && num < 100000000){
				str = StrUtil.getLengthSign("0", length - 8) + str;
			}else if(num > 99999999 && num < 1000000000){
				str = StrUtil.getLengthSign("0", length - 9) + str;
			}else if(num > 999999999 && num < Integer.MAX_VALUE){
				str = StrUtil.getLengthSign("0", length - 10) + str;
			}
		}else{
			str = str.substring(0, length);
		}

		return str;
	}

	/**
	 * 
	 * @param sign
	 *                0
	 * @param length
	 *                3
	 * @return 000
	 */
	public static String getLengthSign(String sign, int length) {
		String str = sign;
		if(length > 0){
			for(int k = 1; k < length; k++){
				str += sign;
			}
		}

		return str;
	}

	public static String concat(String s1, String s2) {
		return s1+s2;
	}

	public static String concat(String s1, String s2, String s3) {
		return s1+s2+s3;
	}

	public static String concat(String s1, String s2, String s3, String s4) {
		return s1+s2+s3+s4;
	}
	
	public static String getHtmlImg(String url, String width, String height) {
		StringBuffer html = new StringBuffer();
		url = StrUtil.trim(url);
		width = StrUtil.trim(width);
		height = StrUtil.trim(height);
		if(!"".equals(url)){
			html.append("<img src='"+url+"'");
			
			if(!"".equals(width)) html.append(" width='"+width+"'");
			if(!"".equals(height)) html.append(" height='"+height+"'");
			
			html.append(" />");
		}
		return html.toString();
	}
	
	public static String getHtmlA(String url, String text, String target) {
		StringBuffer html = new StringBuffer();
		url = StrUtil.trim(url);
		text = StrUtil.trim(text);
		target = StrUtil.trim(target);
		if(!"".equals(url)){
			html.append("<a href='"+url+"'");
			
			if(!"".equals(target)) html.append(" target='"+target+"'");
			
			html.append(">");
			
			if(!"".equals(text)) html.append(text+"");
			
			html.append("</a>");
		}
		return html.toString();
	}
	
	public static String fillStr(String template, String text1) {
		return fillStr(template, new String[]{text1});
	}

	public static String fillStr(String template, String text1, String text2) {
		return fillStr(template, new String[]{text1, text2});
	}

	public static String fillStr(String template, String text1, String text2, String text3) {
		return fillStr(template, new String[]{text1, text2, text3});
	}
	
	public static String fillStr(String template, String text1, String text2, String text3, String text4) {
		return fillStr(template, new String[]{text1, text2, text3, text4});
	}
	
	public static String fillStr(String template, String text1, String text2, String text3, String text4, String text5) {
		return fillStr(template, new String[]{text1, text2, text3, text4, text5});
	}
	
	public static String fillStr(String template, String[] text) {
		Map<String,String> fileNameMap = new HashMap<String,String>();
		String reg = "\\{([0-9]+)\\}";
        Pattern p = Pattern.compile(reg);  
        Matcher m = p.matcher(template);
        String str = null;
        int no = -1;
        while (m.find()) {
         str = m.group(0); // 整个匹配到的内容  
         no = Integer.valueOf(m.group(1)); // [序号]
         if(no-1 < text.length) template = template.replaceAll("\\{"+no+"\\}", text[no-1]);
        }
        
        return template;
	}
	
	//     public static void main(String args[]){
	//	 String str =  "\"aa'aa{aa}aa&aa%aa$aa@aa,aa\\aa:aa;aa=aa+aa-";
	//	 String aa = StrUtils.filter(str);
	//	 
	//	 System.out.println(aa);
	//	 
	//	 System.out.println(StrUtils.unfilter(aa));
	//	 
	//	 System.exit(0);
	//     }

}
