package tools;

import java.io.File;
import java.io.IOException;

public class Paths2 {
	/**
	 * 返回加了file协议的html文件在硬盘上的路径
	 * 
	 * @param relpath 给出相对程序工作目录的相对路径；特别的，eclipse中工作目录是工程所在的目录
	 * @return
	 */
	public static String getPagePath(String relpath) {
		String realPath = "file:///";
		return realPath + getPath(relpath);

	}

	/**
	 * 返回文件在硬盘上的路径
	 * 
	 * @param relpath 给出相对程序工作目录的相对路径；特别的，eclipse中工作目录是工程所在的目录
	 * @return
	 */
	public static String getPath(String relpath) {
		String realPath = "";
		try {
			realPath += new File(".").getCanonicalPath() + File.separator + relpath;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return realPath.replaceAll("\\\\", "/");
	}
}
