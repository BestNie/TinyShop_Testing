package tools;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Paths {
	private static Logger log = LogManager.getLogger(Paths.class);

	/**
	 * 返回加了file协议的html文件在硬盘上的路径
	 * 
	 * @param path 只能是工程下面的文件，path给出相对工程目录的相对路径
	 * @return
	 */
	public static String getPagePath(String path) {
		String realPath = "file:///";
		try {
			realPath += new File(".").getCanonicalPath() + File.separator + path;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		log.debug(realPath.replaceAll("\\\\", "/"));
		return realPath.replaceAll("\\\\", "/");

	}
}
