package com.ctbt.beidou.base.utils;

import java.util.ArrayList;

public class HtmlUtil {

	/**
	 * 特殊字符字典表
	 */
	public static final String[][] FormSpecialCharArray = new String[][] { { "\"", "[KYSC01]" }, { "\'", "[KYSC02]" }, { "{", "[KYSC03]" }, { "}", "[KYSC04]" }, { "&", "[KYSC05]" },
					{ "%", "[KYSC06]" }, { "$", "[KYSC07]" }, { "@", "[KYSC08]" }, { ",", "[KYSC09]" }, { "\\", "[KYSC10]" }, { ":", "[KYSC11]" }, { ";", "[KYSC12]" }, { "=", "[KYSC13]" },
					{ "+", "[KYSC14]" }, { "-", "[KYSC15]" } };

	/**
	 * 过滤特殊字符，主要用于AJAX传递
	 * @param str
	 * @return
	 */
	public static String filter(String str) {
		for(String[] charAry : FormSpecialCharArray){
			str = str.replaceAll(charAry[0], charAry[1]);
		}

		return str;
	}

	/**
	 * 还原特殊字符， 主要用于AJAX传递
	 * @param str
	 * @return
	 */
	public static String unfilter(String str) {
		for(String[] charAry : FormSpecialCharArray){
			str = str.replaceAll(charAry[1], charAry[0]);
		}

		return str;
	}

	/**
	     * <TABLE>
	     * <TR>
	     * <TD>......</TD>
	     * </TR>
	     * </TABLE>
	     * 
	     * @param htmlContent
	     * @param startTag
	     * @param endTag
	     * @return
	     */
	public static ArrayList analyzeTag(String htmlContent, String startTag, String endTag) {
		if(htmlContent == null || "".equals(htmlContent.trim())){
			return null;
		}

		htmlContent = htmlContent.replaceAll("<table", "<TABLE");
		htmlContent = htmlContent.replaceAll("</table", "</TABLE");
		htmlContent = htmlContent.replaceAll("<tr", "<TR");
		htmlContent = htmlContent.replaceAll("</tr", "</TR");
		htmlContent = htmlContent.replaceAll("<td", "<TD");
		htmlContent = htmlContent.replaceAll("</td", "</TD");

		if(startTag == null || "".equals(startTag.trim()) || endTag == null || "".equals(endTag.trim()) || htmlContent.length() < startTag.length() + endTag.length()){
			return null;
		}

		ArrayList<int[]> startTagsList = HtmlUtil.countTags(htmlContent, startTag, 1);
		ArrayList<int[]> endTagsList = HtmlUtil.countTags(htmlContent, endTag, 2);

		// 全部放一起，然后排序
		startTagsList.addAll(endTagsList);
		for(int k = 0; k < startTagsList.size(); k++){
			for(int m = 0; m < startTagsList.size() - 1; m++){
				int[] tag1 = startTagsList.get(m);
				int[] tag2 = startTagsList.get(m + 1);
				if(tag1[1] > tag2[1]){
					startTagsList.set(m, tag2);
					startTagsList.set(m + 1, tag1);
				}
			}
		}

		ArrayList objectStringList = new ArrayList();
		while (startTagsList.size() >= 2){
			for(int p = 0; p < startTagsList.size() - 1; p++){
				int[] tag1 = startTagsList.get(p);
				int[] tag2 = startTagsList.get(p + 1);
				if(tag1[0] == 1 && tag2[0] == 2){
					String cc = htmlContent.substring(tag1[1], tag2[1] + endTag.length() + 1);
					objectStringList.add(cc);

					startTagsList.remove(p + 1);
					startTagsList.remove(p);
				}
			}
		}

		return objectStringList;
	}

	/**
	     * <TD>要取的值</TD>
	     * 
	     * @param content
	     * @return
	     */
	public static String getContentFromTag(String content) {
		int loc1 = content.indexOf(">");
		int loc2 = content.lastIndexOf("<");
		if(loc1 != -1 && loc2 != -1 && loc1 < loc2){
			return content.substring(loc1 + 1, loc2);
		}

		return null;
	}

	private static ArrayList<int[]> countTags(String content, String tag, int flag) {
		ArrayList<int[]> tagsList = new ArrayList<int[]>();
		String tempContent = new String(content);

		int loc = 0;
		while (tempContent.length() > tag.length() && loc > -1){
			loc = tempContent.lastIndexOf(tag);
			if(loc > -1){
				int[] tagInfo = new int[2];
				tagInfo[0] = flag;
				tagInfo[1] = loc;

				tagsList.add(tagInfo);

				tempContent = tempContent.substring(0, loc);
			}
		}

		return tagsList;
	}
}
