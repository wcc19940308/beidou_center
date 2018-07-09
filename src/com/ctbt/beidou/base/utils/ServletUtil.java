package com.ctbt.beidou.base.utils;

import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.ctbt.beidou.base.bo.ResultView;

import net.sf.json.JSONObject;

public class ServletUtil {

	public static void writeResponse(byte[] content, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		OutputStream out = response.getOutputStream();
		out.write(content);
		out.flush();
	}

	public static void writeResponse(byte[] content, HttpServletResponse response, String contentType) throws Exception {
		response.setContentType(contentType);
		response.setHeader("Cache-Control", "no-cache");
		OutputStream out = response.getOutputStream();
		out.write(content);
		out.flush();
	}

	public static void writeResponse(String msg, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.write(msg);
		out.close();
	}

	public static void sendReturnVo(ResultView rv, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		// 返回前台的消息
		JSONObject obj = JSONObject.fromObject(rv);
		String jsonMsg = obj.toString();
//			System.out.println(jsonMsg);

		out.write(jsonMsg);
		out.close();
	}
}
