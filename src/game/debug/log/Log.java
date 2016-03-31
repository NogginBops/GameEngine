package game.debug.log;

import java.util.concurrent.CopyOnWriteArrayList;

import game.debug.log.LogMessage.LogImportance;

public class Log {
	
	private CopyOnWriteArrayList<LogMessage> messages;
	
	public Log() {
		messages = new CopyOnWriteArrayList<>();
	}
	
	public void log(String message, LogImportance impotrance, String ... tagFilter){
		messages.add(new LogMessage(message, impotrance, tagFilter));
		messages.sort(null);
	}
	
	public void logDebug(String message){
		messages.add(new LogMessage(message, LogImportance.DEBUG));
		messages.sort(null);
	}
	
	public void logDebug(String message, String ... tagFilter){
		messages.add(new LogMessage(message, LogImportance.DEBUG, tagFilter));
		messages.sort(null);
	}
	
	public void logMessage(String message){
		messages.add(new LogMessage(message, LogImportance.INFORMATIONAL));
		messages.sort(null);
	}
	
	public void logMessage(String message, String ... tagFilter){
		messages.add(new LogMessage(message, LogImportance.INFORMATIONAL, tagFilter));
		messages.sort(null);
	}
	
	public void logWarning(String message){
		messages.add(new LogMessage(message, LogImportance.WARNING));
		messages.sort(null);
	}
	
	public void logWarning(String message, String ... tagFilter){
		messages.add(new LogMessage(message, LogImportance.WARNING, tagFilter));
		messages.sort(null);
	}
	
	public void logError(String message){
		messages.add(new LogMessage(message, LogImportance.ERROR));
		messages.sort(null);
	}
	
	public void logError(String message, String ... tagFilter){
		messages.add(new LogMessage(message, LogImportance.ERROR, tagFilter));
		messages.sort(null);
	}
	
	public void addMessage(LogMessage message){
		messages.add(message);
		messages.sort(null);
	}
	
	public void removeMessage(LogMessage message){
		messages.remove(message);
		messages.sort(null);
	}
	
	public CopyOnWriteArrayList<LogMessage> getMessages(){
		return messages;
	}
	
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
