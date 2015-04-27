package com.xiaoma.kefu.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 根据 refer 解析搜索引擎关键字
 * @author cuijiabin
 *
 */
public class ParseURLKeywordUtil {
	

	public static String getKeyword(String url) {
		String keywordReg = "(?:yahoo.+?[\\?|&]p=|openfind.+?query=|google.+?q=|lycos.+?query=|onseek.+?keyword=|search\\.tom.+?word=|search\\.qq\\.com.+?word=|zhongsou\\.com.+?word=|search\\.msn\\.com.+?q=|yisou\\.com.+?p=|sina.+?word=|sina.+?query=|sina.+?_searchkey=|sohu.+?word=|sohu.+?key_word=|sohu.+?query=|163.+?q=|baidu.+?wd=|soso.+?w=|3721\\.com.+?p=|Alltheweb.+?q=)([^&]*)";
		String encodeReg = "^(?:[\\x00-\\x7f]|[\\xfc-\\xff][\\x80-\\xbf]{5}|[\\xf8-\\xfb][\\x80-\\xbf]{4}|[\\xf0-\\xf7][\\x80-\\xbf]{3}|[\\xe0-\\xef][\\x80-\\xbf]{2}|[\\xc0-\\xdf][\\x80-\\xbf])+$";
		Pattern keywordPatt = Pattern.compile(keywordReg);
		StringBuffer keyword = new StringBuffer(20);
		Matcher keywordMat = keywordPatt.matcher(url);
		while (keywordMat.find()) {
			keywordMat.appendReplacement(keyword, "$1");
		}
		if (!keyword.toString().equals("")) {
			String keywordsTmp = keyword.toString().replace("http://www.", "");
			Pattern encodePatt = Pattern.compile(encodeReg);
			String unescapeString = ParseURLKeywordUtil.unescape(keywordsTmp);
			Matcher encodeMat = encodePatt.matcher(unescapeString);
			String encodeString = "gbk";
			if (encodeMat.matches())
				encodeString = "utf-8";
			try {
				return URLDecoder.decode(keywordsTmp, encodeString);
			} catch (UnsupportedEncodingException e) {
				return "";
			}
		}
		return "";
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(
							src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(
							src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
	public static void main(String[] args) {
		
		String url = "http://www.baidu.com/s?wd=%E5%B0%8F%E9%A9%AC%E8%BF%87%E6%B2%B3&rsv_spt=1&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=3&rsv_sug1=2&rsv_sug2=0&inputT=2594&rsv_sug4=3963";
		System.out.println(ParseURLKeywordUtil.getKeyword(url));
	}
}
