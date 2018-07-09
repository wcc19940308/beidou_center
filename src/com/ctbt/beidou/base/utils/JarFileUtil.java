package com.ctbt.beidou.base.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import org.apache.log4j.Logger;

public class JarFileUtil {

	private static Logger logger = Logger.getLogger(JarFileUtil.class);
	
	/**
	 * 把jar文件解压缩到指定的路径下
	 * @param jarFilePath
	 * @param outputDir
	 */
	public static void unJar(String jarFilePath, String outputDir) throws Exception {
		JarFile jarFile = new JarFile(jarFilePath);
		Enumeration es = jarFile.entries();
		JarEntry jarEntry = null;
		InputStream in = null;
		FileOutputStream out = null;
		File outFile = null;
		byte[] b;
		while (es.hasMoreElements()){
			jarEntry = (JarEntry) es.nextElement();
			in = jarFile.getInputStream(jarEntry);
			b = new byte[in.available()];
			in.read(b);
			//			System.out.println(jarEntry.getName());

			outFile = new File(outputDir + "\\" + jarEntry.getName());
			createFile(outFile);

			if(outFile.isFile()){
				out = new FileOutputStream(outFile);
				out.write(b);

				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 生成目录和文件
	 * @param file
	 */
	public static void createFile(File file) {
		String path = file.getPath();
		if(path.lastIndexOf(".") > 0){
			// 是文件
			// path.replaceAll("\\", "/");
			String dir = path.substring(0, path.lastIndexOf("\\"));
			String name = path.substring(path.lastIndexOf("\\") + 1, path.length());
			new File(dir).mkdirs();
			if(!file.exists()){
				try{
					file.createNewFile();
				}catch (IOException e){
					logger.error(e.getMessage(), e);
				}
			}
		}else{
			// 是目录
			file.mkdirs();
		}
	}

	/**
	 * 从jar文件中，读取文件内容
	 * @param jarFilePath
	 * @param relativeFilePath
	 * @return
	 * @throws Exception
	 */
	public static byte[] getFileContentFromJarFile(String jarFilePath, String relativeFilePath) throws Exception {
		JarFile jf = new JarFile(jarFilePath);
		Enumeration es = jf.entries();
		while (es.hasMoreElements()){
			JarEntry je = (JarEntry) es.nextElement();
			if(je.getName().equals(relativeFilePath)){
				return getByte(jf.getInputStream(je));
			}
		}

		return null;
	}

	/**
	 * 修改jar文件中的一个文件，用新的内容覆盖
	 * @param jarFilePath
	 * @param relativeFilePath
	 * @param newb
	 * @throws Exception
	 */
	public static void modifyJar(String jarFilePath, String relativeFilePath, byte[] newb) throws Exception {// 修改jar
		JarFile jf = new JarFile(jarFilePath);
		TreeMap tm = new TreeMap();
		Enumeration es = jf.entries();
		while (es.hasMoreElements()){
			JarEntry je = (JarEntry) es.nextElement();
			byte[] b = getByte(jf.getInputStream(je));
			tm.put(je.getName(), b);
		}

		JarOutputStream out = new JarOutputStream(new FileOutputStream(jarFilePath));
		Set set = tm.entrySet();
		Iterator it = set.iterator();
		boolean has = false;
		while (it.hasNext()){
			Map.Entry me = (Map.Entry) it.next();
			String name = (String) me.getKey();
			JarEntry jeNew = new JarEntry(name);
			out.putNextEntry(jeNew);
			byte[] b;
			if(name.equals(relativeFilePath)){
				//找到要修改的文件，用新的内容覆盖
				b = newb;
				has = true;
			}else{
				b = (byte[]) me.getValue();
			}

			out.write(b, 0, b.length);
		}

		//			if (!has) {
		//				//如果没有找到，则追加一个新文件
		//				JarEntry jeNew = new JarEntry(EntryName);
		//				out.putNextEntry(jeNew);
		//				out.write(data, 0, data.length);
		//			}

		out.finish();
		out.close();
	}

	// 从输入取字节
	public static byte[] getByte(java.io.InputStream s) {
		byte[] buffer = new byte[0];
		byte[] chunk = new byte[4096];
		int count;
		try{
			while ((count = s.read(chunk)) >= 0){
				byte[] t = new byte[buffer.length + count];
				System.arraycopy(buffer, 0, t, 0, buffer.length);
				System.arraycopy(chunk, 0, t, buffer.length, count);
				buffer = t;
			}
			s.close();
		}catch (Exception e){
			logger.error(e.getMessage(), e);
		}

		return buffer;
	}
}
