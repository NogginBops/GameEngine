package game.debug.log;

import java.time.Instant;
import java.util.Date;

/**
 * @author Julius Häger
 *
 */
public class LogMessage implements Comparable<LogMessage>{
	
	//JAVADOC: LogMessage
	
	/**
	 * @author Julius Häger
	 *
	 */
	public enum LogImportance{
		/**
		 * 
		 */
		ALERT,
		/**
		 * 
		 */
		CRITICAL,
		/**
		 * 
		 */
		ERROR,
		/**
		 * 
		 */
		WARNING,
		/**
		 * 
		 */
		NOTICE,
		/**
		 * 
		 */
		INFORMATIONAL,
		/**
		 * 
		 */
		DEBUG;
	}
	
	private final String message;
	
	private final LogImportance importance;
	
	private final String[] tags;
	
	private final StackTraceElement[] stackTrace;
	
	private final Date date;
	
	/**
	 * Creates a new log message
	 * @param message
	 */
	public LogMessage(String message){
		date = Date.from(Instant.now());
		stackTrace = Thread.currentThread().getStackTrace();
		
		this.message = message;
		importance = LogImportance.INFORMATIONAL;
		tags = new String[]{};
	}
	
	/**
	 * Creates a new log message with the specified importance
	 * @param message
	 * @param importance
	 */
	public LogMessage(String message, LogImportance importance){
		date = Date.from(Instant.now());
		stackTrace = Thread.currentThread().getStackTrace();
		
		this.importance = importance;
		this.message = message;
		tags = new String[]{};
	}
	
	/**
	 * Creates a new log message with the specified tag(s)
	 * @param message
	 * @param tags
	 */
	public LogMessage(String message, String ... tags){
		date = Date.from(Instant.now());
		stackTrace = Thread.currentThread().getStackTrace();
		
		importance = LogImportance.INFORMATIONAL;
		this.message = message;
		this.tags = tags;
	}
	
	/**
	 * Creates a new log message with the specified importance and tag(s)
	 * @param message
	 * @param importance
	 * @param tags
	 */
	public LogMessage(String message, LogImportance importance, String ... tags){
		date = Date.from(Instant.now());
		stackTrace = Thread.currentThread().getStackTrace();
		
		this.importance = importance;
		this.message = message;
		this.tags = tags;
	}

	/**
	 * Returns the string message of the log message
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Returns the importance of the log message
	 * @return
	 */
	public LogImportance getImportance() {
		return importance;
	}

	/**
	 * Gets the specific date (instance) the log message was created
	 * @return
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Returns a array of all the tags associated with this mesasge
	 * @return
	 */
	public String[] getTags(){
		return tags;
	}
	
	/**
	 * Gets a concatenated semicolon separated string of all the tags associated with this log message.
	 * @return
	 */
	public String getTagsString(){
		String ret = "";
		for (String string : tags) {
			if(string.isEmpty()){
				continue;
			}
			ret += string + ";";
		}
		return ret;
	}

	@Override
	public int compareTo(LogMessage message) {
		return date.compareTo(message.date);
	}
	
	@Override
	public String toString() {
		//NOTE: stackTrace[4] - we are apparently 4 method calls in from the log.logMessage(); method call. 
		//There should maybe be a safer way that guarantees that the callers stack element is shown.
		//One way is to record where the message is being created and use that stack trace.
		return "LogMessage[ \"" + message + "\", Importance: " + importance.toString() + ", Tags: \"" + getTagsString() + "\", At: " + stackTrace[4] + " ]";
	}
}