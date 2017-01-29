package game.debug.log;

import java.time.Instant;
import java.util.Date;

import game.debug.log.Log.LogImportance;

/**
 * @author Julius Häger
 *
 */
public class LogMessage implements Comparable<LogMessage>{
	
	//NOTE: In Java 9 this system could probably be updated to be more efficient in how it get the stack trace
	
	private static int findStackTraceIndex(StackTraceElement[] stackTrace){
		//Start at one to ignore the java.lang.Thread.getStackTrace(Thread.java:<line>) in the beginning.
		for (int i = 1; i < stackTrace.length; i++) {
			try {
				if(Class.forName(stackTrace[i].getClassName()).getPackage() != LogMessage.class.getPackage()){
					return i;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	//JAVADOC: LogMessage
	
	private final String message;
	
	private final LogImportance importance;
	
	private final String[] tags;
	
	private final StackTraceElement[] stackTrace;
	
	private final int stackTraceIndex;
	
	private final Date date;
	
	//NOTE: The constructors don't have a modifier to limit their construction to the Log class (because they are in the same package)
	//This is done to speed up the Log implementation.
	
	/**
	 * Creates a new log message
	 * @param message
	 */
	LogMessage(String message){
		date = Date.from(Instant.now());
		stackTrace = Thread.currentThread().getStackTrace();
		stackTraceIndex = findStackTraceIndex(stackTrace);
		
		this.message = message;
		importance = LogImportance.INFORMATIONAL;
		tags = new String[]{};
	}
	
	/**
	 * Creates a new log message with the specified importance
	 * @param message
	 * @param importance
	 */
	LogMessage(String message, LogImportance importance){
		date = Date.from(Instant.now());
		stackTrace = Thread.currentThread().getStackTrace();
		stackTraceIndex = findStackTraceIndex(stackTrace);
		
		this.importance = importance;
		this.message = message;
		tags = new String[]{};
	}
	
	/**
	 * Creates a new log message with the specified tag(s)
	 * @param message
	 * @param tags
	 */
	LogMessage(String message, String ... tags){
		date = Date.from(Instant.now());
		stackTrace = Thread.currentThread().getStackTrace();
		stackTraceIndex = findStackTraceIndex(stackTrace);
		
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
	LogMessage(String message, LogImportance importance, String ... tags){
		date = Date.from(Instant.now());
		stackTrace = Thread.currentThread().getStackTrace();
		stackTraceIndex = findStackTraceIndex(stackTrace);
		
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
		return "LogMessage[ \"" + message + "\", Importance: " + importance.toString() + ", Tags: \"" + getTagsString() + "\", At: " + stackTrace[stackTraceIndex] + " ]";
	}
}