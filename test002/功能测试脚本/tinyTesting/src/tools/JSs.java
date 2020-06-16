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
	 * jQuery是否加载了
	 */
	public static boolean jqLoaded(JavascriptExecutor jsDriver) {
		return jsObjExist(jsDriver, "jQuery");
	}

	/**
	 * 某个js的对象是否存在
	 */
	public static boolean jsObjExist(JavascriptExecutor jsDriver, String jsObj) {
		return (Boolean) jsDriver.executeScript(String.format("return typeof %s!='undefined';", jsObj));
	}

	/**
	 * 加载jQuery库<br/>
	 * 
	 * @param timeout
	 * @throws JQueryInjectException
	 */
	public static void injectJQuery(JavascriptExecutor jsDriver) throws JQueryInjectException {
		injectJQuery(jsDriver, null);
	}

	/**
	 * 为当前测试的页面加载jquery库1.12.4，版本1可以兼容老的IE。<br/>
	 * 
	 * @param timeout 无法确知什么时候页面加载完（<font color=
	 *                'red'>不是get方法</font>加载的），没法处理时（用元素来判断也不行）采用这个超时，它会在加载jQuery库的脚本代码抛异常的情况下继续执行直到超时。
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
			// 超时之前会一直忽略异常
			try {
				wait.withTimeout(timeout).ignoring(RuntimeException.class).until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver input) {
						try {
							jsDriver.executeScript(injectJQueryc);
						} catch (RuntimeException re) {
							log.error(re);
							// 抛出re会被忽略
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
				log.debug("无等待时间的jqueryload");
			} catch (Exception e) {
				throw new JQueryInjectException("error in jquery loading code", e);
			}
		}
		// 前面的代码顺利执行了，这里如果加载时间超过30秒，可能就是提供jquery库的网站问题

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
	 * 这个方法会定义一个名为"_j2d_"的全局函数。<br/>
	 * 在jQuery代码中，我们可以用_j2d_把jquery节点转换为html的节点。<br/>
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

		// 前面的代码顺利执行了，如果超过3秒还找不到对象_j2d_，就有问题了

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
