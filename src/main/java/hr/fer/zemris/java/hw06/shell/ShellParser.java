package hr.fer.zemris.java.hw06.shell;

public class ShellParser {
	public enum TokenType {
		COMMAND, KEYWORD, ARGUMENT, EOF
	};

	private static class Token {
		private String value;
		private TokenType tokenType;

		public Token(TokenType tokentype, String value) {
			this.value = value;
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

	}

	private static class ShellLexer {

		private char[] data;
		private Token currentToken;
		private int currentIndex;

		public ShellLexer(String text) {
			currentIndex = 0;
			currentToken = null;
			data = text.toCharArray();
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
			if (isEndReached() || isCommand() || isArgument())
				return;
		}

		private boolean isArgument() {
			if (!(Character.isLetter(data[currentIndex]) || data[currentIndex] == '"')) {
				return false;
			}
			String extractedWord = new String("");

			while (currentIndex < data.length && (Character.isLetter(data[currentIndex]))) {
				extractedWord += data[currentIndex];
				currentIndex++;
			}
			currentToken = new Token(TokenType.ARGUMENT, extractedWord);
			return true;

		}

		// charsets, cat, ls,tree, copy, mkdir, hexdump
		private boolean isCommand() {
			String extractedCommand = new String(data, currentIndex, 2);
			if (extractedCommand.equalsIgnoreCase("ls")) {
				currentIndex += extractedCommand.length();
				currentToken = new Token(TokenType.COMMAND, extractedCommand);
				return true;
			}

			extractedCommand = new String(data, currentIndex, 3);

			if (extractedCommand.equalsIgnoreCase("cat")) {
				currentIndex += extractedCommand.length();
				currentToken = new Token(TokenType.COMMAND, extractedCommand);
				return true;
			}

			extractedCommand = new String(data, currentIndex, 4);
			if (extractedCommand.equalsIgnoreCase("copy") || extractedCommand.equalsIgnoreCase("tree")
					|| extractedCommand.equalsIgnoreCase("exit")) {
				currentIndex += extractedCommand.length();
				currentToken = new Token(TokenType.COMMAND, extractedCommand);
				return true;
			}

			extractedCommand = new String(data, currentIndex, 5);
			if (extractedCommand.equalsIgnoreCase("mkdir")) {
				currentIndex += extractedCommand.length();
				currentToken = new Token(TokenType.COMMAND, extractedCommand);
				return true;
			}

			extractedCommand = new String(data, currentIndex, 6);
			if (extractedCommand.equalsIgnoreCase("symbol")) {
				currentIndex += extractedCommand.length();
				currentToken = new Token(TokenType.COMMAND, extractedCommand);
				return true;
			}

			extractedCommand = new String(data, currentIndex, 7);
			if (extractedCommand.equalsIgnoreCase("hexdump")) {
				currentIndex += extractedCommand.length();
				currentToken = new Token(TokenType.COMMAND, extractedCommand);
				return true;
			}

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
}
