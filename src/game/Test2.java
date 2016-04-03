package game;

import game.controller.event.EventListener;
import game.controller.event.EventMachine;
import game.controller.event.GameEvent;
import game.debug.log.Log;
import game.debug.log.LogMessage;
import game.debug.log.LogMessage.LogImportance;

@SuppressWarnings("javadoc")
public class Test2 {

	public static void main(String[] args) {
		new Test2();
		//logTest();
	}
	
	public static void logTest(){
		Log log = new Log();
		
		log.addMessage(new LogMessage("asdf.sssssewrgf"));
		
		log.addMessage(new LogMessage("import", LogImportance.ALERT));
		
		log.addMessage(new LogMessage("printing", LogImportance.ALERT, "asfd", "graphics"));
		
		log.addMessage(new LogMessage("message", "tag1", "tag2", "tag3"));
		
		for (LogMessage message : log.getMessages(LogImportance.NOTICE, "graphics")) {
			System.out.println(message);
		}
	}

	public Test2() {
		EventMachine eventMachine = new EventMachine();

		EventListener listener = new EventListener() {

			@Override
			public <T extends GameEvent> void eventFired(T event) {
				System.out.println("Listener1 recived event: " + event);
				if (event instanceof CustomEvent) {
					System.out.println("Event message = \"" + ((CustomEvent) event).message + "\"");
				}
			}
		};

		eventMachine.addEventListener(CustomEvent.class, listener);

		eventMachine.fireEvent(new CustomEvent(this, "Test message"));
	}

	private class CustomEvent extends GameEvent {

		public final String message;

		public CustomEvent(Object origin, String message) {
			super(origin, "Test");
			this.message = message;
		}
	}
}
