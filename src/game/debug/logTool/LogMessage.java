package game.debug.logTool;

import java.time.Instant;
import java.util.Date;

public class LogMessage implements Comparable<LogMessage>{
	
	public enum LogImportance{
		ALERT,
		CRITICAL,
		ERROR,
		WARNING,
		NOTICE,
		INFORMATIONAL,
		DEBUG;
	}
	
	private final String message;
	
	private final LogImportance importance;
	
	private final String[] tags;
	
	private final Date date;
	
	public LogMessage(String message){
		date = Date.from(Instant.now());
		
		this.message = message;
		importance = LogImportance.INFORMATIONAL;
		tags = new String[]{};
	}
	
	public LogMessage(String message, LogImportance importance){
		date = Date.from(Instant.now());
		
		this.importance = importance;
		this.message = message;
		tags = new String[]{};
	}
	
	public LogMessage(String message, String ... tags){
		date = Date.from(Instant.now());
		
		importance = LogImportance.INFORMATIONAL;
		this.message = message;
		this.tags = tags;
	}
	
	public LogMessage(String message, LogImportance importance, String ... tags){
		date = Date.from(Instant.now());
		
		this.importance = importance;
		this.message = message;
		this.tags = tags;
	}

	public String getMessage() {
		return message;
	}
	
	public LogImportance getImportance() {
		return importance;
	}

	public Date getDate() {
		return date;
	}
	
	public String[] getTags(){
		return tags;
	}
	
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
		return "LogMessage[ " + message + ", Importance: " + importance.toString() + ", Tags: " + getTagsString() + " ]";
	}
}