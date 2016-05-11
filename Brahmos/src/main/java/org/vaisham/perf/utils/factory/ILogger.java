package org.vaisham.perf.utils.factory;

import org.vaisham.perf.enums.LogLevel;

/**
 * interface for logging trace messages
 * @author (KH1871) Amit Khandelwal
 *
 */
public interface ILogger {

	/**
	 * log a trace message
	 * @param level - level at which message is logged like ERROR
	 * @param msg- actual message to be logged
	 */
	void logMessage(LogLevel level,String msg);

	/**
	 * log a exception to trace system
	 * @param level- level at which message is logged like ERROR
	 * @param e - exception object which has to be logged
	 */
	void logException(LogLevel level,Exception e);

	/**
	 * overload method to log optional message along with exception
	 * @param level- level at which message is logged like ERROR
	 * @param msg- message to be logged
	 * @param e - exception object which has to be logged
	 */
	void logException(LogLevel level,String msg,Exception e);

	/**
	 * set log level at which logger should be logging
	 * @param level- level at which message should be allowed to log e.g if set at error
	 * then only error and above message are traced
	 */
	void setLogLevel(LogLevel level);
}
