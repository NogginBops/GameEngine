package game.debug.log;

import java.util.concurrent.CopyOnWriteArrayList;

import game.debug.log.LogMessage.LogImportance;

/**
 * @author Julius Häger
 *
 */
public class Log {
	
	//JAVADOC: Log
	
	private CopyOnWriteArrayList<LogMessage> messages;
	
	/**
	 * 
	 */
	public boolean printDebug = false;
	
	/**
	 * Creates a empty log.
	 */
	public Log() {
		messages = new CopyOnWriteArrayList<>();
	}
	
	/**
	 * @param message
	 */
	public void logMessage(LogMessage message){
		messages.add(message);
		messages.sort(null);
		
		//TODO: Fix?
		if(message.getImportance().compareTo(LogImportance.ERROR) <= 0){
			System.err.println(message.toString());
		}else{
			if(message.getImportance() != LogImportance.DEBUG || printDebug){
				System.out.println(message.toString());
			}
		}
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
		logMessage(new LogMessage(message, impotrance, tagFilter));
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.DEBUG)}
	 * @param message
	 */
	public void logDebug(String message){
		log(message, LogImportance.DEBUG);
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.DEBUG, tagFilter)}
	 * @param message
	 * @param tagFilter
	 */
	public void logDebug(String message, String ... tagFilter){
		log(message, LogImportance.DEBUG, tagFilter);
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.INFORMATIONAL)}
	 * @param message
	 */
	public void logMessage(String message){
		log(message, LogImportance.INFORMATIONAL);
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.INFORMATIONAL, tagFilter)}
	 * @param message
	 * @param tagFilter
	 */
	public void logMessage(String message, String ... tagFilter){
		log(message, LogImportance.INFORMATIONAL, tagFilter);
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.WARNING)}
	 * @param message
	 */
	public void logWarning(String message){
		log(message, LogImportance.WARNING);
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.WARNING, tagFilter)}
	 * @param message
	 * @param tagFilter
	 */
	public void logWarning(String message, String ... tagFilter){
		log(message, LogImportance.WARNING, tagFilter);
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.ERROR)}
	 * @param message
	 */
	public void logError(String message){
		log(message, LogImportance.ERROR);
	}
	
	/**
	 * Convenience method for {@link #log(message, LogImportance.ERROR, tagFilter)}
	 * @param message
	 * @param tagFilter
	 */
	public void logError(String message, String ... tagFilter){
		log(message, LogImportance.ERROR, tagFilter);
	}
	
	/**
	 * Gets all the messages in this log
	 * @return
	 */
	public CopyOnWriteArrayList<LogMessage> getMessages(){
		return messages;
	}
	
	/**
	 * Gets all the messages in this log using the specified filter(s)
	 * @param importanceFilter 
	 * @return
	 */
	public CopyOnWriteArrayList<LogMessage> getMessages(LogImportance importanceFilter){
		if(importanceFilter == LogImportance.DEBUG){
			return getMessages();
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
			return getMessages();
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
