package game.debug.log;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * @author Julius Häger
 *
 */
public class Log {
	
	//JAVADOC: Log
	
	/**
	 * @author Julius Häger
	 *
	 */
	public enum LogImportance{
		
		//JAVADOC: LogImportance
		
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
	
	//TODO: Clean up the different method calls so that they make more sense (e.g. the calls logDebug() -> log() -> logMessage() does not make sense)
	//While doing this one should look at optimizing the add so that you don't have to sort, because that can take a lot of CPU when dealing with a lot of messages
	
	/**
	 * 
	 */
	private LogImportance acceptLevel = LogImportance.INFORMATIONAL;
	
	private ConcurrentLinkedQueue<LogMessage> messages;
	
	private Consumer<LogMessage> logReader;
	
	/**
	 * Creates a empty log.
	 * @param logReader 
	 */
	public Log(Consumer<LogMessage> logReader) {
		this.logReader = logReader;
		messages = new ConcurrentLinkedQueue<>();
	}
	
	/**
	 * Creates a empty log with a default logReader the prints out the log to System.out.
	 */
	public Log() {
		this.logReader = (message) -> { System.out.println(message); };
		messages = new ConcurrentLinkedQueue<>();
	}
	
	/**
	 * @param importance
	 */
	public void setAcceptLevel(LogImportance importance){
		acceptLevel = importance;
	}
	
	/**
	 * @return
	 */
	public LogImportance getAcceptLevel(){
		return acceptLevel;
	}
	
	/**
	 * @param reader
	 */
	public void addReader(Consumer<LogMessage> reader) {
		logReader = logReader.andThen(reader); //NOTE: This will work well for a small number of readers, might create a long call chain though.
	}
	
	/**
	 * Logs a message.
	 * 
	 * @param message
	 * the text of the message
	 * @param impotrance
	 * the importance of the message
	 * @param tagFilter
	 * the tags associated with the message (for easy filtering of messages)
	 */
	public void log(String message, LogImportance impotrance, String ... tagFilter){
		if(acceptLevel.compareTo(impotrance) < 0) return; //If the acceptLevel is too high ignore the message
		
		LogMessage logMessage = new LogMessage(message, impotrance, tagFilter);
		
		messages.add(logMessage);
		
		if(logReader != null){
			logReader.accept(logMessage);
		}
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.DEBUG)}
	 * @param message
	 */
	public void logDebug(String message){
		if(acceptLevel.compareTo(LogImportance.DEBUG) < 0) return; //If the acceptLevel is too high ignore the message
		
		log(message, LogImportance.DEBUG);
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.DEBUG, tagFilter)}
	 * @param message
	 * @param tagFilter
	 */
	public void logDebug(String message, String ... tagFilter){
		if(acceptLevel.compareTo(LogImportance.DEBUG) < 0) return; //If the acceptLevel is too high ignore the message
		
		log(message, LogImportance.DEBUG, tagFilter);
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.INFORMATIONAL)}
	 * @param message
	 */
	public void logMessage(String message){
		if(acceptLevel.compareTo(LogImportance.INFORMATIONAL) < 0) return; //If the acceptLevel is too high ignore the message

		log(message, LogImportance.INFORMATIONAL);
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.INFORMATIONAL, tagFilter)}
	 * @param message
	 * @param tagFilter
	 */
	public void logMessage(String message, String ... tagFilter){
		if(acceptLevel.compareTo(LogImportance.INFORMATIONAL) < 0) return; //If the acceptLevel is too high ignore the message

		log(message, LogImportance.INFORMATIONAL, tagFilter);
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.WARNING)}
	 * @param message
	 */
	public void logWarning(String message){
		if(acceptLevel.compareTo(LogImportance.WARNING) < 0) return; //If the acceptLevel is too high ignore the message

		log(message, LogImportance.WARNING);
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.WARNING, tagFilter)}
	 * @param message
	 * @param tagFilter
	 */
	public void logWarning(String message, String ... tagFilter){
		if(acceptLevel.compareTo(LogImportance.WARNING) < 0) return; //If the acceptLevel is too high ignore the message

		log(message, LogImportance.WARNING, tagFilter);
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.ERROR)}
	 * @param message
	 */
	public void logError(String message){
		if(acceptLevel.compareTo(LogImportance.ERROR) < 0) return; //If the acceptLevel is too high ignore the message

		log(message, LogImportance.ERROR);
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.ERROR, tagFilter)}
	 * @param message
	 * @param tagFilter
	 */
	public void logError(String message, String ... tagFilter){
		if(acceptLevel.compareTo(LogImportance.ERROR) < 0) return; //If the acceptLevel is too high ignore the message

		log(message, LogImportance.ERROR, tagFilter);
	}
	
	/**
	 * Gets all the messages in this log
	 * @return
	 */
	public ConcurrentLinkedQueue<LogMessage> getMessages(){
		return messages;
	}
	
	/**
	 * Gets all the messages in this log using the specified filter(s)
	 * @param importanceFilter 
	 * @return
	 */
	public CopyOnWriteArrayList<LogMessage> getMessages(LogImportance importanceFilter){
		if(importanceFilter == LogImportance.DEBUG){
			return new CopyOnWriteArrayList<>(getMessages());
		}
		
		CopyOnWriteArrayList<LogMessage> returnList = new CopyOnWriteArrayList<>();
		for (LogMessage logMessage : messages) {
			if(logMessage.getImportance().ordinal() <= importanceFilter.ordinal()){
				returnList.add(logMessage);
			}
		}
		return returnList;
	}
	
	/**
	 * Gets all the messages in this log using the specified filter(s)
	 * @param tagFilter 
	 * @return
	 */
	public CopyOnWriteArrayList<LogMessage> getMessages(String ... tagFilter){
		if(tagFilter.length == 0){
			return new CopyOnWriteArrayList<>(getMessages());
		}
		
		CopyOnWriteArrayList<LogMessage> returnList = new CopyOnWriteArrayList<>();
		for (LogMessage logMessage : messages) {
			message: for (String messageTag : logMessage.getTags()) {
				for (String filter : tagFilter) {
					if(filter.isEmpty()){
						continue;
					}
					if(messageTag.equalsIgnoreCase(filter)){
						returnList.add(logMessage);
						break message;
					}
				}
			}
		}
		return returnList;
	}
	
	/**
	 * Gets all the messages in this log using the specified filter(s)
	 * @param importanceFilter 
	 * @param tagFilter 
	 * @return
	 */
	public CopyOnWriteArrayList<LogMessage> getMessages(LogImportance importanceFilter, String ... tagFilter){
		if(importanceFilter == LogImportance.DEBUG){
			return getMessages(tagFilter);
		}
		
		CopyOnWriteArrayList<LogMessage> returnList = new CopyOnWriteArrayList<>();
		for (LogMessage logMessage : messages) {
			if(logMessage.getImportance().ordinal() <= importanceFilter.ordinal()){
				message: for (String messageTag : logMessage.getTags()) {
					for (String filter : tagFilter) {
						if(filter.isEmpty()){
							continue;
						}
						if(messageTag.equalsIgnoreCase(filter)){
							returnList.add(logMessage);
							break message;
						}
					}
				}
			}
		}
		return returnList;
	}
}
