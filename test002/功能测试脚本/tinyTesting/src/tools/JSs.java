package tools;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JSs {
	private static Logger log = LogManager.getLogger(JSs.class);

	/**
	 * jQuery�Ƿ������
	 */
	public static boolean jqLoaded(JavascriptExecutor jsDriver) {
		return jsObjExist(jsDriver, "jQuery");
	}

	/**
	 * ĳ��js�Ķ����Ƿ����
	 */
	public static boolean jsObjExist(JavascriptExecutor jsDriver, String jsObj) {
		return (Boolean) jsDriver.executeScript(String.format("return typeof %s!='undefined';", jsObj));
	}

	/**
	 * ����jQuery��<br/>
	 * 
	 * @param timeout
	 * @throws JQueryInjectException
	 */
	public static void injectJQuery(JavascriptExecutor jsDriver) throws JQueryInjectException {
		injectJQuery(jsDriver, null);
	}

	/**
	 * Ϊ��ǰ���Ե�ҳ�����jquery��1.12.4���汾1���Լ����ϵ�IE��<br/>
	 * 
	 * @param timeout �޷�ȷ֪ʲôʱ��ҳ������꣨<font color=
	 *                'red'>����get����</font>���صģ���û������ʱ����Ԫ�����ж�Ҳ���У����������ʱ�������ڼ���jQuery��Ľű��������쳣������¼���ִ��ֱ����ʱ��
	 * @throws JQueryInjectException
	 */
	public static void injectJQuery(JavascriptExecutor jsDriver, Duration timeout) throws JQueryInjectException {
		String injectJQueryc = 
				"var ohead =document.head || document.getElementsByTagName('head')[0];\r\n" + 
				"var oscript = document.createElement('script');\r\n" + 
				"var url = \"//cdn.bootcss.com/jquery/1.12.4/jquery.min.js\";\r\n" + 
				"var protocol =  window.location.protocol==='https:'?'https:':'http:';\r\n" + 
				"url = protocol+url;\r\n" + 
				"oscript.type = 'text/javascript';\r\n" + 
				"ohead.appendChild(oscript);\r\n" + 
				"oscript.src = url;";
		WebDriverWait wait = new WebDriverWait((WebDriver) jsDriver, 0);
		if (timeout != null) {
			// ��ʱ֮ǰ��һֱ�����쳣
			try {
				wait.withTimeout(timeout).ignoring(RuntimeException.class).until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver input) {
						try {
							jsDriver.executeScript(injectJQueryc);
						} catch (RuntimeException re) {
							log.error(re);
							// �׳�re�ᱻ����
							throw re;
						}
						return true;
					}
				});
			} catch (Exception e) {
				throw new JQueryInjectException(
						"jquery loading code timeout,root cause ignored,but log into error.log.", e);
			}
		} else {
			try {
				jsDriver.executeScript(injectJQueryc);
				log.debug("�޵ȴ�ʱ���jqueryload");
			} catch (Exception e) {
				throw new JQueryInjectException("error in jquery loading code", e);
			}
		}
		// ǰ��Ĵ���˳��ִ���ˣ������������ʱ�䳬��30�룬���ܾ����ṩjquery�����վ����

		try {
			wait.withTimeout(Duration.ofSeconds(30)).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver input) {
					return JSs.jqLoaded((JavascriptExecutor) input);
				}
			});
		} catch (Exception e) {
			throw new JQueryInjectException("jquery lib loading timeout", e);
		}
	}

	/**
	 * ��������ᶨ��һ����Ϊ"_j2d_"��ȫ�ֺ�����<br/>
	 * ��jQuery�����У����ǿ�����_j2d_��jquery�ڵ�ת��Ϊhtml�Ľڵ㡣<br/>
	 */
	public static void def_j2d_(JavascriptExecutor jsDriver) throws JQueryInjectException {
		String def_j2d_c = 
				"if(typeof window._j2d_ =='undefined'){\r\n" + 
				"	window._j2d_=function(cells){\r\n" + 
				"		var htmlCells=new Array();\r\n" + 
				"		for(i=0;i<cells.length;i++){\r\n" + 
				"			htmlCells[i]=cells.get(i);\r\n" + 
				"		}\r\n" + 
				"		return htmlCells;\r\n" + 
				"	}\r\n" + 
				"}";
		try {
			jsDriver.executeScript(def_j2d_c);
		} catch (Exception e) {
			throw new JQueryInjectException("error in _j2d_ function defining code", e);
		}

		// ǰ��Ĵ���˳��ִ���ˣ��������3�뻹�Ҳ�������_j2d_������������

		try {
			new WebDriverWait((WebDriver) jsDriver, 3).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver input) {
					return JSs.jsObjExist((JavascriptExecutor) input, "_j2d_");
				}
			});
		} catch (Exception e) {
			throw new JQueryInjectException("_j2d_ function defining code timeout", e);
		}
	}

	public static class JQueryInjectException extends Exception {
		private static final long serialVersionUID = 1L;

		public JQueryInjectException(String message, Throwable cause) {
			super(message, cause);
		}
	}

}
