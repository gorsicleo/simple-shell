package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * Class provides functuionality for both parsing user commands and command
 * arguments. To provide that functionality there are two distinct states. Upon
 * creation string is being parsed.
 * 
 * @author gorsicleo
 *
 */
public class ShellParser {
	
	/**This enum contains possible token types that lexer can generate
	 * @author gorsicleo*/
	public static enum TokenType {
		COMMAND, KEYWORD, ARGUMENT, EOF
	};

	/**This enum contains two modes for parsing:
	 * @author gorsicleo*/
	public static enum ParsingMode {
		
		/**State for parsing input when user is entering commands in shell.*/
		USER_INPUT_PARSING,
		/**State for parsing command arguments <b>FROM already parsed user input</b>.*/
		ARGUMENTS_PARSING
	};

	/**Class that models token that lexer is generating. 
	 * @author gorsicleo
	 */
	public static class Token {
		private String value;
		private TokenType tokenType;

		/**Creates new token.*/
		public Token(TokenType tokentype, String value) {
			this.value = value;
			this.tokenType = tokentype;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public TokenType getType() {
			return tokenType;
		}

		public void setType(TokenType tokenType) {
			this.tokenType = tokenType;
		}

		/**Converts token to string using template: [tokenType | tokenValue].*/
		@Override
		public String toString() {
			return "[ " + tokenType.toString() + " | " + value + " ]";
		}

	}

	/**Class that performs lexical analysis of given input.
	 * Lexical units are tokens that hold type and value.
	 * Input is being tokenized upon creation of object.
	 * @author gorsicleo
	 */
	private static class ShellLexer {

		private char[] data;
		private Token currentToken;
		private int currentIndex;
		private ParsingMode parsingMode;

		/**Creates new Lexer object and starts lexical analysis.*/
		public ShellLexer(String text, ParsingMode parsingMode) {
			currentIndex = 0;
			currentToken = null;
			data = text.toCharArray();
			this.parsingMode = parsingMode;
		}

		/**Get last created token.*/
		public Token getToken() {
			return currentToken;
		}

		/**Generate and return next token.*/
		public Token nextToken() {
			generateNextToken();
			return currentToken;
		}

		/** Calls methods to identify token. */
		private void generateNextToken() {
			removeBlanks();
			if (parsingMode == ParsingMode.USER_INPUT_PARSING) {
				if (isEndReached() || isCommand() || isArguments())
					return;
			}

			if (parsingMode == ParsingMode.ARGUMENTS_PARSING) {
				if (isEndReached() || isArgument())
					return;
			}

			throw new ShellLexerException("Illegal lexer state");

		}

		/**
		 * Method checks if following characters can be grouped as one argument. Method is
		 * used when lexer is working in arguments parsing.
		 * 
		 * @return true if following characters are possible to group as one word
		 *         (characters separated by spaces)
		 */
		private boolean isArgument() {

			String extractedWord = new String("");

			while (currentIndex < data.length && data[currentIndex] != ' ') {
				if (data[currentIndex] == '"') {
					do {
						extractedWord += data[currentIndex];
						currentIndex++;
					} while (data[currentIndex] != '"');
					extractedWord += data[currentIndex];
					currentIndex++;
					break;
				} else {
					extractedWord += data[currentIndex];
					currentIndex++;
				}

			}
			currentToken = new Token(TokenType.ARGUMENT, extractedWord);
			return true;

		}

		/**
		 * Method checks if following characters can be grouped as one argument. Method is
		 * used when lexer is working in user input parsing.
		 * 
		 * @return true if following characters are possible to group as one word
		 *         (characters separated by spaces)
		 */
		private boolean isArguments() {
			String extractedArguments = "";
			while (currentIndex < data.length) {
				extractedArguments += data[currentIndex];
				currentIndex++;
			}
			currentToken = new Token(TokenType.ARGUMENT, extractedArguments);
			return true;
		}

		/**Method checks if following characters can be grouped as command.
		 * Currently supported commands::
		 * charsets, cat, ls,tree, copy, mkdir, hexdump
		 */
		private boolean isCommand() {
			
			if (data.length - currentIndex < 2) return false;
			
			String extractedCommand = new String(data, currentIndex, 2);
			if (extractedCommand.equalsIgnoreCase("ls")) {
				handleExtractedCommand(extractedCommand);
				return true;
			}

			if (data.length - currentIndex < 3) return false;

			extractedCommand = new String(data, currentIndex, 3);
			if (extractedCommand.equalsIgnoreCase("cat")) {
				handleExtractedCommand(extractedCommand);
				return true;
			}

			if (data.length - currentIndex < 4) return false;

			extractedCommand = new String(data, currentIndex, 4);
			if (extractedCommand.equalsIgnoreCase("copy") || extractedCommand.equalsIgnoreCase("tree")
					|| extractedCommand.equalsIgnoreCase("exit") || extractedCommand.equalsIgnoreCase("help")) {
				handleExtractedCommand(extractedCommand);
				return true;
			}

			if (data.length - currentIndex < 5) return false;

			extractedCommand = new String(data, currentIndex, 5);
			if (extractedCommand.equalsIgnoreCase("mkdir")) {
				handleExtractedCommand(extractedCommand);
				return true;
			}

			if (data.length - currentIndex < 6) return false;

			extractedCommand = new String(data, currentIndex, 6);
			if (extractedCommand.equalsIgnoreCase("symbol")) {
				handleExtractedCommand(extractedCommand);
				return true;
			}

			if (data.length - currentIndex < 7) return false;

			extractedCommand = new String(data, currentIndex, 7);
			if (extractedCommand.equalsIgnoreCase("hexdump")) {
				handleExtractedCommand(extractedCommand);
				return true;
			}

			if (data.length - currentIndex < 8) return false;

			extractedCommand = new String(data, currentIndex, 8);
			if (extractedCommand.equalsIgnoreCase("charsets")) {
				handleExtractedCommand(extractedCommand);
				return true;
			}

			return false;
		}

		/**Method creates new COMMAND token*/
		private void handleExtractedCommand(String extractedCommand) {
			currentIndex += extractedCommand.length();
			currentToken = new Token(TokenType.COMMAND, extractedCommand);
		}

		/**
		 * Method checks if current index count reached length of string that is used
		 * for lexical analysis. If true EOF token is being created.
		 * 
		 * @return true if current index count is equal or greater than length of
		 *         current string that is being analysed.
		 */
		private boolean isEndReached() {
			if (currentIndex >= data.length) {
				currentToken = new Token(TokenType.EOF, null);
				return true;
			} else {
				return false;
			}

		}

		/**
		 * Method increases internal index count every time next character is blank, new
		 * line, tabulator.
		 * 
		 */
		private void removeBlanks() {
			while (currentIndex < data.length) {
				char currentCharacter = data[currentIndex];
				if (currentCharacter == ' ' || currentCharacter == '\n' || currentCharacter == '\t'
						|| currentCharacter == '\r') {
					currentIndex++;
				} else {
					break;
				}
			}
		}
	}

	/** Lexer used for generating lexer tokens */
	private ShellLexer lexer;

	/** List of all tokens generated by lexer */
	private List<Token> tokens = new ArrayList<>();

	/**
	 * Parses query string.
	 * 
	 * @param input string for parsing query
	 */
	public ShellParser(String input, ParsingMode parsingMode) {
		lexer = new ShellLexer(input, parsingMode);
		getAllTokens();
	}

	/** Calls lexer until EOF is reached and stores all tokens in internal list */
	private void getAllTokens() {
		tokens.add(lexer.nextToken());
		while (lexer.nextToken().getType() != TokenType.EOF) {
			tokens.add(lexer.getToken());
		}

	}

	public List<Token> getTokens() {
		return tokens;
	}
}
