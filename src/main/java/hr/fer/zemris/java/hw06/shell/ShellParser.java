package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

public class ShellParser {
	public enum TokenType {
		COMMAND, KEYWORD, ARGUMENT, EOF
	};

	public enum ParsingMode {
		USER_INPUT_PARSING, ARGUMENTS_PARSING
	};

	public static class Token {
		private String value;
		private TokenType tokenType;

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

		@Override
		public String toString() {
			return "[ " + tokenType.toString() + " | " + value + " ]";
		}

	}

	private static class ShellLexer {

		private char[] data;
		private Token currentToken;
		private int currentIndex;
		private ParsingMode parsingMode;

		public ShellLexer(String text,ParsingMode parsingMode) {
			currentIndex = 0;
			currentToken = null;
			data = text.toCharArray();
			this.parsingMode = parsingMode;
		}

		public Token getToken() {
			return currentToken;
		}

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

		private boolean isArgument() {
			
			String extractedWord = new String("");

			while (currentIndex < data.length && data[currentIndex] != ' ') {
				if (data[currentIndex]=='"') {
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
		private boolean isArguments() {
			String extractedArguments = "";
			while (currentIndex < data.length) {
				extractedArguments += data[currentIndex];
				currentIndex++;
			}
			currentToken = new Token(TokenType.ARGUMENT, extractedArguments);
			return true;
		}

		// charsets, cat, ls,tree, copy, mkdir, hexdump
		private boolean isCommand() {
			if (data.length - currentIndex < 2)
				return false;
			String extractedCommand = new String(data, currentIndex, 2);
			if (extractedCommand.equalsIgnoreCase("ls")) {
				currentIndex += extractedCommand.length();
				currentToken = new Token(TokenType.COMMAND, extractedCommand);
				return true;
			}
			
			if (data.length - currentIndex < 3)
				return false;

			extractedCommand = new String(data, currentIndex, 3);

			if (extractedCommand.equalsIgnoreCase("cat")) {
				currentIndex += extractedCommand.length();
				currentToken = new Token(TokenType.COMMAND, extractedCommand);
				return true;
			}
			
			if (data.length - currentIndex < 4)
				return false;

			extractedCommand = new String(data, currentIndex, 4);
			if (extractedCommand.equalsIgnoreCase("copy") || extractedCommand.equalsIgnoreCase("tree")
					|| extractedCommand.equalsIgnoreCase("exit") || extractedCommand.equalsIgnoreCase("help")) {
				currentIndex += extractedCommand.length();
				currentToken = new Token(TokenType.COMMAND, extractedCommand);
				return true;
			}
			
			if (data.length - currentIndex < 5)
				return false;

			extractedCommand = new String(data, currentIndex, 5);
			if (extractedCommand.equalsIgnoreCase("mkdir")) {
				currentIndex += extractedCommand.length();
				currentToken = new Token(TokenType.COMMAND, extractedCommand);
				return true;
			}
			
			if (data.length - currentIndex < 6)
				return false;

			extractedCommand = new String(data, currentIndex, 6);
			if (extractedCommand.equalsIgnoreCase("symbol")) {
				currentIndex += extractedCommand.length();
				currentToken = new Token(TokenType.COMMAND, extractedCommand);
				return true;
			}
			
			if (data.length - currentIndex < 7)
				return false;

			extractedCommand = new String(data, currentIndex, 7);
			if (extractedCommand.equalsIgnoreCase("hexdump")) {
				currentIndex += extractedCommand.length();
				currentToken = new Token(TokenType.COMMAND, extractedCommand);
				return true;
			}
			
			if (data.length - currentIndex < 8)
				return false;

			extractedCommand = new String(data, currentIndex, 8);
			if (extractedCommand.equalsIgnoreCase("charsets")) {
				currentIndex += extractedCommand.length();
				currentToken = new Token(TokenType.COMMAND, extractedCommand);
				return true;
			}

			return false;
		}

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

	/**Parses query string.
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
