package game;

import game.controller.event.EventListener;
import game.controller.event.EventMachine;
import game.controller.event.GameEvent;

@SuppressWarnings("javadoc")
public class Test2 {
	
	public static void main(String[] args){
		new Test2();
	}
	
	public Test2() {
		EventMachine eventMachine = new EventMachine();
		
		EventListener listener = new EventListener() {
			
			@Override
			public <T extends GameEvent> void eventFired(T event) {
				System.out.println("Listener1 recived event: " + event);
				if(event instanceof CustomEvent){
					System.out.println("Event message = \"" + ((CustomEvent)event).message + "\"");
				}
			}
		};
		
		eventMachine.addEventListener(CustomEvent.class, listener);
		
		eventMachine.fireEvent(new CustomEvent(this, "Test message"));
	}
	
	private class CustomEvent extends GameEvent {
		
		public final String message;
		
		public CustomEvent(Object origin, String message) {
			super(origin);
			this.message = message;
		}

		@Override
		public int compareTo(GameEvent arg0) {
			return 0;
		}
	}
}
