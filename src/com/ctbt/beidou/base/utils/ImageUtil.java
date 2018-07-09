package com.ctbt.beidou.base.utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {

	/**
	 * 按比例缩放，加水印
	 * @param imageFile
	 * @param newWidth
	 * @param newHeight
	 * @param watermark
	 * @param watermarkLoc
	 * @return
	 * @throws Exception
	 */
	public static Dimension zoomImage(File imageFile, Integer imagePercent, Integer newWidth, Integer newHeight, String watermark, String watermarkLoc) throws Exception {
		if(imageFile == null) return null;

		FileInputStream input = new FileInputStream(imageFile);
		BufferedImage bufImage = ImageIO.read(input);
		if(bufImage == null) return null;

		int oldWidth = bufImage.getWidth();
		int oldHeight = bufImage.getHeight();

		int newW = oldWidth;
		int newH = oldHeight;
		boolean hasCreate = false;
		BufferedImage newBufImage = null;
		if(imagePercent != null){
			//按比例缩放
			float p = (float) imagePercent / (float) 100;
			newW = (int) ((float) oldWidth * p);
			newH = (int) ((float) oldHeight * p);
		}else if(newWidth != null && newHeight != null){
			//指定新的宽度高度
			newW = newWidth;
			newH = newHeight;
		}
		
		if(newW > 1000){
			//宽度大于1000，按比例缩放到1000
			float p = (float) 1000 / (float) newW;
			newW = 1000;
			newH = (int) ((float) oldHeight * p);
		}

		if(newW != oldWidth){
			//宽度有调整，需要重新生成
			hasCreate = true;
			newBufImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
			newBufImage.getGraphics().drawImage(bufImage.getScaledInstance(newW, newH, Image.SCALE_SMOOTH), 0, 0, null);
		}
		
		System.out.println("oldWidth:" + oldWidth + "/oldHeight:" + oldHeight+ " , newWidth:" + newW+ "/newHeight:" + newH);
		
		//加水印
		if(!"".equals(StrUtil.trim(watermark))){
			Font font = new Font("宋体", Font.BOLD, 20);
			FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(font);
			int fh = fm.getHeight();//字符高度
			int fw = fm.stringWidth(watermark);//字符串 长度
			int x = 0;
			int y = 0;
			int s = 10;//边距空隙
			if("LT".equals(watermarkLoc)){
				//左上
				x = s;
				y = s;
			}else if("RT".equals(watermarkLoc)){
				//右上
				x = newW - fw - s;
				y = s;
			}else if("LB".equals(watermarkLoc)){
				//左下
				x = s;
				y = newH - fh - s;
			}else{
				//默认 RB 右下
				x = newW - fw - s;
				y = newH - fh - s;
			}

			System.out.println("fh:" + fh + "/fw:" + fw + "/x:" + x + "/y:" + y);
			hasCreate = true;
			if(newBufImage != null){
				newBufImage.getGraphics().drawString(watermark, x, y);
			}else{
				bufImage.getGraphics().drawString(watermark, x, y);
			}
		}

		if(hasCreate){
			FileOutputStream out = new FileOutputStream(imageFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			if(newBufImage != null){
				encoder.encode(newBufImage);
			}else{
				encoder.encode(bufImage);
			}
			out.close();
		}
		
		input.close();

		return new Dimension(newW, newH);
	}

	/**
	 * 保存图片文件
	 * @param bufImage
	 * @param dirPath
	 * @param fileName
	 * @throws Exception 
	 */
	public static void saveImageFile(BufferedImage bufImage, String dirPath, String fileName) throws Exception {
		if(bufImage == null) return;

		String filePath = dirPath + File.separator + fileName;
		File dir = new File(dirPath);
		if(!dir.exists()){
			dir.mkdirs();
		}

		FileOutputStream out = new FileOutputStream(filePath);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(bufImage);
		out.close();
	}

}
