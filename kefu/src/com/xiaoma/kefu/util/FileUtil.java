package com.xiaoma.kefu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoma.kefu.dict.DictMan;

/**
 * 文件操作	工具类
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月14日下午4:27:17
**********************************
 */
public class FileUtil {
	
	/**
	 * 保存文件到服务器
	* @Description: TODO
	* @param savePath	保存的路径,结尾不需要带 /
	* @param fileName	文件名 ,需要带后缀名
	* @param fileData	文件
	 * @throws IOException 
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public static void saveFile(String savePath,String fileName,MultipartFile fileData) throws IOException{
		File fileDir = new File(savePath);
        if (!fileDir.exists()) {		//先创建目录
            fileDir.mkdirs();
        }
        FileOutputStream out;
		try {
			out = new FileOutputStream(savePath + "/"+ fileName);
			out.write(fileData.getBytes());
		    out.flush();
		    out.close();
		} catch (IOException e) {
			throw e;
		}
	}
	
	/**
	 * 获取风格上传图片的	根路径
	 * @param styleId 	风格id
	* @Description: TODO
	* @return	rootpath/style/styleId
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	public static String getStyleRootPath(Integer styleId) {
		if(styleId==null) styleId=0;
		return 
				DictMan.getDictItem("d_sys_param", 1).getItemName()
				+"/" + SysConst.STYLE_PATH
				+"/" + styleId;
	}
	
	
	/**
	 * 删除文件, 如果是文件夹,则删除文件夹及下面所有文件
	* @param dir
	* @return	如果出错一个,则停止删除,返回false, 全成功返回true
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}
	
	/**
	 * 根据指定路径,删除文件
	 * 如果给的路径是个文件夹,则删除文件夹及下面所有文件
	* @param path
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月19日
	 */
	public static boolean deleteDir(String path){
		return deleteDir(new File(path));
	}
	
	/**
	 * 
	* @param strDate	yyyy-MM-dd 格式
	* @return	总跟路径/导出对话根目录/年/月
	* @Author: wangxingfei
	* @Date: 2015年4月23日
	 */
	public static String getExpTalkRootPath(String strDate) {
		String basePath = DictMan.getDictItem("d_sys_param", 1).getItemName()
				+"/" + SysConst.EXP_TALK_PATH;
		if(StringUtils.isNotBlank(strDate)){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(strDate);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				basePath += "/"+calendar.get(Calendar.YEAR) + "/" +calendar.get(Calendar.MONTH);
			} catch (ParseException e) {
				
			}
		}
		return basePath;
	}
	
	
}
