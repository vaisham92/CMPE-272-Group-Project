package org.vaisham.perf.utils.factory;

/**
 * 
 * @author KH1868
 *
 */
public interface ILoggerFactory {
	/**
	 * Get logger object with the given name
	 * @param loggerName - name of the logger
	 * @return ILogger object
	 */
	ILogger getLogger(String loggerName);
}
