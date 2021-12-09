package hr.fer.zemris.java.hw06.shell;

public class MyShell {

	public static void main(String[] args) {
		EnvironmentImpl environment = new EnvironmentImpl();
		
		while (true) {
			environment.readLine();
		}
	}
}
