package com.xiaoma.kefu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
			out = new FileOutputStream(savePath + "\\"+ fileName);
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
}
