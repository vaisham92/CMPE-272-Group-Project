package org.vaisham.perf.utils.factory;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.vaisham.perf.enums.LogLevel;

/**
 * 
 * @author KH1868
 *
 */
public class Log4jLogger implements ILogger {
	private final String loggerName;
	private LogLevel logLevel;
	private final AbstractLogger logger; 
	private static final String FQCN = Log4jLogger.class.getName();

	private Level getLog4jLevel(LogLevel level) {
		switch(level){
		case FATAL:
			return Level.FATAL;
		case DEBUG:
			return Level.DEBUG;
		case ERROR:
			return Level.ERROR;
		case INFO:
			return Level.INFO;
		case WARN:
			return Level.WARN;
		default:
			return Level.INFO;

		}
	}
	public Log4jLogger(String loggerName) {
		this.loggerName=loggerName;
		this.logLevel=LogLevel.INFO;
		logger=(AbstractLogger) LogManager.getLogger(loggerName);		
	}

	public void logMessage(LogLevel level, String msg) {		
	  logger.logIfEnabled(FQCN, getLog4jLevel(level), null, msg);
	}

	public void setLogLevel(LogLevel level) {		
		logLevel=level;
	}

	public void logException(LogLevel level, Exception e) {
		logger.logIfEnabled(FQCN, getLog4jLevel(level), null,"",e);  
	}

	public void logException(LogLevel level, String msg,Exception e) {
		logger.logIfEnabled(FQCN, getLog4jLevel(level), null,msg,e);
	}

}
