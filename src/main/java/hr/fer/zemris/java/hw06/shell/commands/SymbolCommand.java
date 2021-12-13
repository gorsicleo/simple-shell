package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellParser.ParsingMode;
import hr.fer.zemris.java.hw06.shell.ShellParser.Token;

/**
 * Models symbol command. Symbol command is used for displaying and changing
 * shell symbols.
 * 
 * @author gorsicleo
 */
public class SymbolCommand implements ShellCommand {

	private static final String CHANGE_SYMBOL = "Symbol %s changed from '%c' to '%s'";
	private static final String PRINT_SYMBOL = "Symbol for %s is ' %s'";
	private static final String INVALID_SYMBOL_MESSAGE = "Please choose one of the following symbol names: [PROMPT | MORELINES | MULTILINE]";
	private static final String INVALID_NUMBER_OF_ARGS = "Invalid number of arguments for method symbol should be max 2, but was: ";
	private static final String INVALID_ARG_SIZE = "Argument length not valid: should be character, but was String (%d)";
	private static final String COMMAND_NAME = "symbol";
	private static final List<String> COMMAND_DESCRIPTION = List.of("Command for changing symbols",
			"symbol [symbol-type] [new-symbol] ",
			"\r\r symbol-type - Type of symbol to be changed [PROMPT | MORELINES | MULTILINE]",
			"\r\r new-symbol - New symbol [# | % | $ | ...]");

	private List<Token> tokens;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		tokens = new ShellParser(arguments, ParsingMode.ARGUMENTS_PARSING).getTokens();

		if (tokens.size() == 1) {
			handleSymbolPrint(env);
		}

		else if (tokens.size() == 2) {
			String newSymbol = tokens.get(1).getValue();
			if (newSymbol.length() != 1) {
				return CommandUtil.terminateNotFatal(String.format(INVALID_ARG_SIZE, newSymbol.length()), env);
			}

			handleSymbolChange(env, newSymbol);
		} else {
			env.writeln(INVALID_NUMBER_OF_ARGS + tokens.size());
		}
		return ShellStatus.CONTINUE;

	}

	private void handleSymbolChange(Environment env, String newSymbol) {
		switch (tokens.get(0).getValue()) {
		case "PROMPT":
			env.writeln(String.format(CHANGE_SYMBOL, "PROMPT", env.getPromptSymbol(), newSymbol));
			env.setPromptSymbol(newSymbol.charAt(0));
			break;

		case "MORELINES":
			env.writeln(String.format(CHANGE_SYMBOL, "MORELINES", env.getMorelinesSymbol(), newSymbol));
			env.setMorelinesSymbol(newSymbol.charAt(0));
			break;

		case "MULTILINE":
			env.writeln(String.format(CHANGE_SYMBOL, "MULTILINE", env.getMultilineSymbol(), newSymbol));
			env.setMultilineSymbol(newSymbol.charAt(0));
			break;

		default:
			env.writeln(INVALID_SYMBOL_MESSAGE);
			break;
		}
	}

	private void handleSymbolPrint(Environment env) {
		switch (tokens.get(0).getValue()) {
		case "PROMPT":
			env.writeln(String.format(PRINT_SYMBOL, "PROMPT", env.getPromptSymbol()));

			break;

		case "MORELINES":
			env.writeln(String.format(PRINT_SYMBOL, "MORELINES", env.getMorelinesSymbol()));
			break;

		case "MULTILINE":
			env.writeln(String.format(PRINT_SYMBOL, "MULTILINE", env.getMultilineSymbol()));
			break;

		default:
			env.writeln(INVALID_SYMBOL_MESSAGE);
			break;
		}
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return COMMAND_DESCRIPTION;
	}

}
