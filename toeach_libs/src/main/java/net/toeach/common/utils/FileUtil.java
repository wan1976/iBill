package net.toeach.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;

/**
 * 文件工具类<br/>
 * net.toeach.common.utils.FileUtil
 * @author 万云  <br/>
 * @version 1.0
 */
public class FileUtil {
	/**
	 * 读取文件内容<br/>
	 * @param fileName 文件名
	 * @return 文件内容
	 * @throws IOException 异常
	 */
	public static String readFile(String fileName) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		StringBuffer sb = new StringBuffer();
		String data = null;
		while ((data = in.readLine()) != null) {
			sb.append(data).append("\r\n");
		}
		in.close();
		return sb.toString();
    }
	
	/**
	 * 写入文件<br/>
	 * @param fileName 文件名
	 * @param append 是否追加
	 * @param content 写入内容
	 * @throws IOException 异常
	 */
	public static void writeFile(String fileName, boolean append, String content) throws IOException {
		content = content+"\r\n";
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName, append));
		out.write(content);
		out.close();
	}
}
