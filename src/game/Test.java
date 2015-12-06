package game;

public class Test {
	
	//TODO: Test: Remove/Fix startup

	public static void main(String args[]) {
		Test test = new Test();
		test.run();
	}

	private Game game;

	public Test() {
		game = new Game();
	}

	public void run() {
		game.run();
	}
}
