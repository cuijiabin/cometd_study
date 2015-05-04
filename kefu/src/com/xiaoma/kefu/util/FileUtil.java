package com.xiaoma.kefu.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
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
	
	
	/**
	 * 根据风格id,生成风格js文件
	* @param styleId
	* @throws Exception
	* @Author: wangxingfei
	* @Date: 2015年4月27日
	 */
	public static void createStyleJsFile(Integer styleId) throws Exception{
		BufferedReader br = null;
		PrintStream ps = null;
		try{
			String sourcePath = 
					DictMan.getDictItem("d_sys_param", 16).getItemName()
//					"E:/space/.metadata/.plugins/org.eclipse.wst.server.core/tmp3/wtpwebapps/kefu"
					+ "/" + SysConst.TEMPLATE_PATH
					+ "/" + SysConst.JS_DIV_TEMPLATE ;
			br = new BufferedReader(new FileReader(new File(sourcePath)));
			
			String targePath =  
					DictMan.getDictItem("d_sys_param", 16).getItemName()
//					"E:/space/.metadata/.plugins/org.eclipse.wst.server.core/tmp3/wtpwebapps/kefu"
					+ "/" + SysConst.JS_DIV_PATH
					+ "/" + SysConst.JS_NAME + styleId + ".js" ;
			
			ps = new PrintStream(new File(targePath));
			ps.println("var styleId = "+ styleId +";");//第一行写入styleId
			String siteAdd = DictMan.getDictItem("d_sys_param", 15).getItemName();
			String line = null;
			while ((line = br.readLine()) != null) {
				if(StringUtils.isNotBlank(line) && line.indexOf("${siteAdd}") >0 ){
					line = line.replace("${siteAdd}", siteAdd);
				}
				ps.println(line);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			try {
				br.close();
				ps.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 复制文件
	 * 
	 * @param source
	 *            原文件
	 * @param target
	 *            目标文件
	 * @Author: wangxingfei
	 * @Date: 2015年4月28日
	 */
	public static void copyFile(File source, File target) {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			if(!target.getParentFile().exists()){
				target.getParentFile().mkdirs();
			}
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(source));
			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(target));
			// 缓冲数组
			byte[] b = new byte[2048];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inBuff != null)
					inBuff.close();
				if (outBuff != null)
					outBuff.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 复制文件
	* @param sourcePath	源路径
	* @param targetPath	目标路径
	* @Author: wangxingfei
	* @Date: 2015年4月28日
	 */
	public static void copyFile(String sourcePath, String targetPath){
		copyFile(new File(sourcePath),new File(targetPath));
	}
	
	public static void main(String[] args) throws Exception{
//		createStyleJsFile(3);
		copyFile("E:/space/.metadata/.plugins/org.eclipse.wst.server.core/tmp3/wtpwebapps/kefu/style/template/ydInviteIcon.png","E:/space/.metadata/.plugins/org.eclipse.wst.server.core/tmp3/wtpwebapps/kefu/upload/style/99/servicePCon.png");
	}
	
}
