package com.ctbt.beidou.base.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	/**
	 * 复制文件
	 * 
	 * @param sourcePath
	 *                如：c:/hhh.txt 必须是文件，文件类型不限
	 * @param targetPath
	 *                如：d:/hhh.txt 或者可以是一个目录，如d:/
	 * @throws Exception
	 */
	public static void copyFile(String sourcePath, String targetPath) throws Exception {
		File sourceFile = new File(sourcePath);
		File targetFile = new File(targetPath);
		copyFile(sourceFile, targetFile);
	}

	public static void copyFile(File sourceFile, File targetFile) throws Exception {
		if(sourceFile.exists() && sourceFile.isFile()){ // 如果源文件存在
			if(!targetFile.exists()){
				// 如果目标不存在,不能直接判断是文件还是目录
				if(targetFile.getPath().indexOf(".") > 0){
					// 带.号，则认为目标是文件
					String path = targetFile.getParent();
					// path = path.replace('\\', '/');
					// String subPath = path.substring(0,
					// path.lastIndexOf("/"));// 文件名以上的路径
					File driFile = new File(path);
					driFile.mkdirs();
				}else{
					// 没有.号，则认为是目录
					targetFile.mkdirs();
				}
			}

			if(targetFile.isDirectory()){
				targetFile = new File(targetFile.getPath() + File.separator + sourceFile.getName());
			}

			// 创建新文件的目录成功
			FileInputStream input = new FileInputStream(sourceFile);
			FileOutputStream output = new FileOutputStream(targetFile);

			int length = input.available();
			byte[] readB = new byte[1024];
			int b = 1024;// 每次读取/写入 文件块的大小
			if(length <= b){
				readB = new byte[length];
			}else{
				readB = new byte[b];
			}

			while (input.read(readB) > 0){
				output.write(readB);

				length = length - readB.length;
				if(length <= b){
					readB = new byte[length];
				}else{
					readB = new byte[b];
				}
			}

			output.close();
			input.close();
		}
	}

	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *                如d:/aa/bb
	 * @return boolean
	 */
	public static void newFolder(String folderPath) {
		String fpath = folderPath.replaceAll("/", "\\\\");
		File targetFile = new File(fpath);
		if(!targetFile.exists()){
			// 如果目标不存在,不能直接判断是文件还是目录,通过带不带.号来判断是文件还是目录
			// 如果.号在第一个位置，认为是文件夹,因为文件名不可能以.号开头，不合法
			String fileName = fpath.substring(fpath.lastIndexOf("\\") + 1);
			if(fileName.indexOf(".") > 1){
				// 带.号，则认为目标是文件
				String path = targetFile.getParent();
				File driFile = new File(path);
				driFile.mkdirs();
			}else{
				// 没有.号，或者.号在第一个位置，则认为是目录
				targetFile.mkdirs();
			}
		}
	}
	
	public static void newFolder(File file) {
		newFolder(file.getPath());
	}

	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *                文件路径及名称 如d:/aaa.txt
	 * @param fileContent
	 *                byte[] 文件内容
	 * @return boolean
	 */
	public static void newFile(String filePathAndName, byte[] fileContent) throws Exception {
		File targetFile = new File(filePathAndName);
		newFile(targetFile, fileContent);
	}

	public static void newFile(File targetFile, byte[] fileContent) throws Exception {
		if(!targetFile.exists()){
			newFolder(targetFile);
		}

		FileOutputStream output = new FileOutputStream(targetFile);

		int length = fileContent.length;
		int b = 1024;// 每次读取/写入 文件块的大小
		byte[] temp = new byte[b];
		int c = 0;
		while (length > 0){

			if(length >= b){
				// 剩余内容长度还有多余一个单位b
				System.arraycopy(fileContent, c * b, temp, 0, b);
			}else{
				// 剩余内容还有，但不足一个单位b
				temp = new byte[length];
				System.arraycopy(fileContent, c * b, temp, 0, length);
			}

			output.write(temp);
			length -= b;
			c++;
		}

		output.flush();
		output.close();
	}
	
	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *                文件路径及名称 如 d:/aaa.txt 如果是文件，直接删除；
	 *                如果是文件目录，如果该目录下有其他文件，则不能直接删除，须先删除其下文件
	 */
	public static boolean deleteFile(String filePathAndName) {
		File myDelFile = new File(filePathAndName);
		return deleteFile(myDelFile);
	}

	public static boolean deleteFile(File file) {
		return file.delete();
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *                String 文件夹, 如d:/hhh
	 */
	public static void deleteFolder(String folderPath) throws Exception {
		cleanFolder(folderPath); // 删除完里面所有内容
		File myFilePath = new File(folderPath);
		myFilePath.delete(); // 删除空文件夹
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *                String 文件夹, 如d:/hhh
	 */
	public static void deleteFolder(File folder) throws Exception {
		cleanFolder(folder); // 删除完里面所有内容
		folder.delete(); // 删除空文件夹
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *                String 文件夹路径 如 c:/fqf
	 */
	public static void cleanFolder(String folderPath) throws Exception {
		File folder = new File(folderPath);
		cleanFolder(folder);
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *                String 文件夹路径 如 c:/fqf
	 */
	public static void cleanFolder(File folder) throws Exception {
		if(!folder.exists()){
			return;
		}

		if(!folder.isDirectory()){
			return;
		}

		File[] tempList = folder.listFiles();
		File temp = null;
		for(int i = 0; i < tempList.length; i++){
			temp = tempList[i];

			if(temp.isFile()){
				temp.delete();
			}else if(temp.isDirectory()){
				deleteFolder(temp);// 先删除文件夹里面的文件
			}
		}
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *                String 原文件路径 如：d:/zzz
	 * @param newPath
	 *                String 复制后路径 如：d:/hhh
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) throws Exception {
		(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
		File a = new File(oldPath);
		String[] file = a.list();
		File temp = null;
		for(int i = 0; i < file.length; i++){
			if(oldPath.endsWith(File.separator)){
				temp = new File(oldPath + file[i]);
			}else{
				temp = new File(oldPath + File.separator + file[i]);
			}

			if(temp.isFile()){
				FileInputStream input = new FileInputStream(temp);
				FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = input.read(b)) != -1){
					output.write(b, 0, len);
				}
				output.flush();
				output.close();
				input.close();
			}
			if(temp.isDirectory()){// 如果是子文件夹
				copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
			}
		}

	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param source
	 *                String 如：d:/aaa.txt
	 * @param target
	 *                String 如：d:/ddd/aaa.txt
	 */
	public static boolean moveFile(String source, String target) throws Exception {
		return moveFile(new File(source), new File(target));
	}

	public static boolean moveFile(File sourceFile, File targetFile) throws Exception {
		copyFile(sourceFile, targetFile);
		return deleteFile(sourceFile);
	}

	/**
	 * 将一个目录下的所有内容，移动到指定的目录下
	 * 
	 * @param oldPath
	 *                String 如：c:/zzz/*.*
	 * @param newPath
	 *                String 如：d:/hhh/*.*
	 */
	public static void moveFolder(String oldPath, String newPath) throws Exception {
		copyFolder(oldPath, newPath);
		deleteFolder(oldPath);
	}

	/**
	 * 从指定的目录下找到指定文件名的文件列表,如果有子目录，也会去找；
	 * 
	 * @param folder
	 *                d:/hhh
	 * @param fileName
	 *                如 aaa.java
	 * @return
	 * @throws Exception
	 */
	public static List<File> findFile(File folder, String fileName) throws Exception {
		List<File> findFiles = new ArrayList<File>();
		File tempFile = null;
		if(folder.exists() && folder.isDirectory()){
			File[] fileList = folder.listFiles();
			for(int k = 0; k < fileList.length; k++){
				tempFile = fileList[k];
				if(tempFile.isFile() && tempFile.getName().equalsIgnoreCase(fileName)){
					findFiles.add(tempFile);
				}else if(tempFile.isDirectory()){
					// 如果是目录，往下一级找；
					findFiles.addAll(findFile(tempFile, fileName));
				}
			}
		}

		return findFiles;
	}

	/**
	 * 找到指定目录下的所有文件，如果有子目录，也会去找；
	 * @param folder
	 * @return
	 * @throws Exception
	 */
	public static List<File> findFileList(File folder) throws Exception {
		List<File> findFiles = new ArrayList<File>();
		File tempFile = null;
		if(folder.exists() && folder.isDirectory()){
			File[] fileList = folder.listFiles();
			for(int k = 0; k < fileList.length; k++){
				tempFile = fileList[k];
				if(tempFile.isFile()){
					findFiles.add(tempFile);
				}else if(tempFile.isDirectory()){
					// 如果是目录，往下一级找；
					findFiles.addAll(findFileList(tempFile));
				}
			}
		}

		return findFiles;
	}

	/**
	 * 比对两个目录下的文件，找出层级相同、文件名相同，但是文件大小 或者 修改时间不同的文件
	 * @param foldera
	 * @param folderb
	 * @return
	 */
	public static ArrayList<String> findFileChangeList(File foldera, File folderb) throws Exception {
		ArrayList<String> changeAry = new ArrayList<String>();

		if(foldera.exists() && folderb.exists() && foldera.isDirectory() && folderb.isDirectory()){
			String roota = foldera.getPath();
			String rootb = folderb.getPath();
			List<File> lista = findFileList(foldera);
			List<File> listb = findFileList(folderb);
			File filea = null;
			File fileb = null;
			String aa = "";
			String bb = "";
			for(int k = lista.size() - 1; k >= 0; k--){
				filea = lista.get(k);

				for(int m = listb.size() - 1; m >= 0; m--){
					fileb = listb.get(m);

					aa = filea.getPath().substring(roota.length());
					bb = fileb.getPath().substring(rootb.length());
					if(aa.equals(bb)){
						//两个文件，各自截去 根目录的路径，剩下的如果相同，则认为是 目录层级相同，且文件名相同
						if(filea.getTotalSpace() != fileb.getTotalSpace() || filea.lastModified() != fileb.lastModified()){
							//文件大小 或者 文件最后修改时间 不一致，则认为修改过了
							String modifiedTimea = DateUtil.date2String(DateUtil.getDateFromTimeMillis(filea.lastModified()), DateUtil.FORMAT_DATETIME);
							String modifiedTimeb = DateUtil.date2String(DateUtil.getDateFromTimeMillis(fileb.lastModified()), DateUtil.FORMAT_DATETIME);
							String detail = "[" + filea.length() + "," + fileb.length() + "]-[" + modifiedTimea + "," + modifiedTimeb + "]";
							changeAry.add(aa + detail);
						}
					}
				}
			}
		}

		return changeAry;
	}

	/**
	 * 读取文件内容，以byte的形式
	 * 
	 * @param sourceFile
	 * @return
	 * @throws Exception
	 */
	public static byte[] getFileToByte(File sourceFile) throws Exception {
		FileInputStream in = new FileInputStream(sourceFile);
		byte[] b = new byte[in.available()];
		in.read(b);
		in.close();

		return b;
	}

	/**
	 * 获得文本文件的内容
	 * 
	 * @param sourceFile
	 * @return
	 * @throws Exception
	 */
	public static String getFileToString(File sourceFile) throws Exception {

		FileInputStream in = new FileInputStream(sourceFile);
		byte[] b = new byte[in.available()];
		in.read(b);
		in.close();

		if(b[0] == -17 && b[1] == -69 && b[2] == -65){
			// "编码为UTF-8"
			return new String(b, "utf-8");
		}else{
			// 可能是GBK
			return new String(b, "GBK");
		}
	}

	/**
	 * 把文件列表根据文件名排序；
	 * 
	 * @param fileList
	 * @throws Exception
	 */
	public static void sortByFileName(List<File> fileList) throws Exception {

	}

	/**
	 * 把文件列表根据最后的修改时间排序，从小到大
	 * 
	 * @param fileList
	 * @throws Exception
	 */
	public static void sortByModifyTime(List<File> fileList) throws Exception {

	}

	/**
	 * 替换文件名,文件扩展名不变
	 * 
	 * @param fileName
	 * @param newFileName
	 */
	public static String changeFileName(String fileName, String newFileName) {
		String name = fileName;
		if(name != null){
			int s1 = name.lastIndexOf("/");
			int s2 = name.lastIndexOf("\\");
			int s = Math.max(s1, s2);
			String n1 = "";
			String n2 = name;
			if(s > -1){
				n1 = name.substring(0, s + 1);
				n2 = name.substring(s + 1, name.length());
			}

			int s3 = n2.lastIndexOf(".");
			String n3 = n2;
			String n4 = "";
			if(s3 > -1){
				n3 = n2.substring(0, s3);
				n4 = n2.substring(s3, n2.length());//待"."
			}

			n3 = newFileName;

			name = n1 + n3 + n4;
		}

		return name;
	}

	/**
	 * 根据文件路径，解析出文件路径、文件名、文件类型、原路径
	 * @param pathWithName
	 * @return strs[文件路径,文件名,文件类型,原路径];
	 */
	public static String[] getFileNameType(String pathWithName) {
		String[] strs = new String[4];
		String path = pathWithName;
		path = path.replaceAll("\\\\", "/");
		if(path.lastIndexOf("/") > -1){
			//有带斜杠有路径信息
			strs[0] = path.substring(0, path.lastIndexOf("/") + 1);
			strs[1] = path.substring(path.lastIndexOf("/") + 1, path.length());
			if(strs[1].lastIndexOf(".") > -1){
				strs[2] = strs[1].substring(strs[1].lastIndexOf(".") + 1, strs[1].length());
				strs[1] = strs[1].substring(0, strs[1].lastIndexOf("."));
			}
		}else{
			//没有斜杠，及没有路径信息，只有文件名
			strs[0] = "";
			strs[1] = path;
			if(strs[1].lastIndexOf(".") > -1){
				strs[2] = strs[1].substring(strs[1].lastIndexOf(".") + 1, strs[1].length());
				strs[1] = strs[1].substring(0, strs[1].lastIndexOf("."));
			}
		}

		strs[3] = pathWithName;

		return strs;
	}

	/**
	 * 判断文件扩展名 是不是 图片类型
	 * @param pathWithName
	 * @return
	 */
	public static boolean isImage(String pathWithName) {
		if("".equals(StrUtil.trim(pathWithName))) return false;
		
		pathWithName = pathWithName.toLowerCase();
		if(pathWithName.endsWith(".jpg")
						|| pathWithName.endsWith(".jpeg")
						|| pathWithName.endsWith(".gif")
						|| pathWithName.endsWith(".png")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 两个目录相减
	 * @param foldera
	 * @param folderb
	 * @return
	 */
	public static ArrayList<String> folderSub(File foldera, File folderb) {
		ArrayList<String> subAry = new ArrayList<String>();

		if(foldera.exists() && folderb.exists()){
			String[] lista = foldera.isDirectory() ? foldera.list() : new String[] { foldera.getName() };
			String[] listb = folderb.isDirectory() ? folderb.list() : new String[] { folderb.getName() };
			for(int k = 0; k < lista.length; k++){
				for(String fnb : listb){
					if(lista[k].equalsIgnoreCase(fnb)){
						//把相同的至Null,剩下的就是ListB中没有的
						lista[k] = null;
						break;
					}
				}
			}

			for(String fna : lista){
				if(fna != null){
					subAry.add(fna);
				}
			}
		}

		return subAry;
	}

	/**
	 * 两个目录相加
	 * @param foldera
	 * @param folderb
	 * @return
	 */
	public static ArrayList<String> folderSum(File foldera, File folderb) {
		ArrayList<String> sumAry = new ArrayList<String>();

		if(foldera.exists() && folderb.exists()){
			String[] lista = foldera.isDirectory() ? foldera.list() : new String[] { foldera.getName() };
			String[] listb = folderb.isDirectory() ? folderb.list() : new String[] { folderb.getName() };
			for(String fna : lista){
				if(!sumAry.contains(fna)){
					sumAry.add(fna);
				}
			}

			for(String fnb : listb){
				if(!sumAry.contains(fnb)){
					sumAry.add(fnb);
				}
			}
		}

		return sumAry;
	}

	/**
	 * 两个目录相交
	 * @param foldera
	 * @param folderb
	 * @return
	 */
	public static ArrayList<String> folderIntersect(File foldera, File folderb) {
		ArrayList<String> intersectAry = new ArrayList<String>();

		if(foldera.exists() && folderb.exists()){
			String[] lista = foldera.isDirectory() ? foldera.list() : new String[] { foldera.getName() };
			String[] listb = folderb.isDirectory() ? folderb.list() : new String[] { folderb.getName() };
			for(String fna : lista){
				for(String fnb : listb){
					if(fna.equals(fnb)){
						intersectAry.add(fna);
						break;
					}
				}
			}
		}

		return intersectAry;
	}
	
	/**
	 * 读取单个网页内容
	 * @param url
	 * @param ecode
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer readWebPage(String url, String ecode) throws Exception{
		StringBuffer content = new StringBuffer();
		URL targetUrl = null;
		HttpURLConnection httpUrlConn = null;

		targetUrl = new URL(url);
		httpUrlConn = (HttpURLConnection) targetUrl.openConnection();
		httpUrlConn.setRequestProperty("User-agent", "Mozilla/4.0");
		httpUrlConn.setUseCaches(false); // 设置不使用缓存
		httpUrlConn.setConnectTimeout(60000);
		httpUrlConn.setReadTimeout(60000);
		httpUrlConn.connect();

		InputStream in = httpUrlConn.getInputStream();
		BufferedReader read = new BufferedReader(new InputStreamReader(in, ecode));
		String line = null;
		while ((line = read.readLine()) != null){
			content.append(line + "\n");
		}

		httpUrlConn.disconnect();
		read.close();
		return content;
	}
	
	/**
	 * 读取web页面，保存到指定的文件
	 * @param url
	 * @param ecode1
	 * @param targetFile
	 * @param ecode2
	 * @throws Exception
	 */
	public static void saveWebPage2File(String url, String ecode1, String targetFile, String ecode2) throws Exception{
		StringBuffer strBuf = FileUtil.readWebPage(url, ecode1);
		FileUtil.newFile(targetFile, strBuf.toString().getBytes(ecode2));
	}
}
