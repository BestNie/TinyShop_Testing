package tools;

import java.io.File;
import java.io.IOException;

public class Paths2 {
	/**
	 * ���ؼ���fileЭ���html�ļ���Ӳ���ϵ�·��
	 * 
	 * @param relpath ������Գ�����Ŀ¼�����·�����ر�ģ�eclipse�й���Ŀ¼�ǹ������ڵ�Ŀ¼
	 * @return
	 */
	public static String getPagePath(String relpath) {
		String realPath = "file:///";
		return realPath + getPath(relpath);

	}

	/**
	 * �����ļ���Ӳ���ϵ�·��
	 * 
	 * @param relpath ������Գ�����Ŀ¼�����·�����ر�ģ�eclipse�й���Ŀ¼�ǹ������ڵ�Ŀ¼
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
