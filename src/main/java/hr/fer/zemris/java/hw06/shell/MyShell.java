package hr.fer.zemris.java.hw06.shell;

public class MyShell {

	public static void main(String[] args) {
		EnvironmentImpl environment = new EnvironmentImpl();
		String userInput;
		
		while (true) {
			userInput = environment.readLine();
			ShellParser parser = new ShellParser(userInput);
			System.out.println(parser.getTokens());
		}
	}
}
