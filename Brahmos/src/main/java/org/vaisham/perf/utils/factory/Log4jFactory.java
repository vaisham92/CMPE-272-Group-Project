package org.vaisham.perf.utils.factory;

/**
 * 
 * @author KH1868
 *
 */
public class Log4jFactory implements ILoggerFactory {

	public ILogger getLogger(String loggerName) {		
		return new Log4jLogger(loggerName);
	}
}
