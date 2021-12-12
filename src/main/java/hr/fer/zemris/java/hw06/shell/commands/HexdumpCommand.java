package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellParser.ParsingMode;
import hr.fer.zemris.java.hw06.shell.ShellParser.Token;

public class HexdumpCommand implements ShellCommand {

	private static final String UNKNOWN_CHARACTER = ".";
	private static final String COMMAND_NAME = "hexdump";
	private static final List<String> COMMAND_DESCRIPTION = List.of(
			"Display hex-output in a specified format", 
			"hexdump [path] ", 
			"\r\r path - Path to source file.");
	
	private List<Token> tokens;
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		tokens = new ShellParser(arguments, ParsingMode.ARGUMENTS_PARSING).getTokens();
		
		if (tokens.size() != 1) {
			env.writeln("Invalid number of arguments for method symbol should be 1, but was: "+tokens.size());
			return ShellStatus.CONTINUE;
		}
		
		String path = tokens.get(0).getValue().startsWith("\"") ? tokens.get(0).getValue().replace("\"", "")
				: tokens.get(0).getValue();
		
		File file = new File(path);
		
		if (file.isDirectory()) {
			env.writeln("You must provide path to file, not directory.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			env.write(convertFileToHexDump(file.toPath()));
		} catch (ShellIOException e) {
			env.writeln("Fatal error.");
			return ShellStatus.TERMINATE;
		} catch (IOException e) {
			env.writeln("Error occured while opening file.");
			return ShellStatus.CONTINUE;
		}
		
		
		return ShellStatus.CONTINUE;
		
		
	}
	
	 public static String convertFileToHexDump(Path path) throws IOException {

	        String result = new String();
	        String hex = new String();
	        String input = new String();

	        int countElements = 0;
	        int countRows = 0;
	        int value;

	        // path to inputstream....
	        try (InputStream inputStream = Files.newInputStream(path)) {

	            while ((value = inputStream.read()) != -1) {
	                hex += (String.format("%02X ", value));

	                //If the character is unable to convert, just prints a dot "."
	                
	                input += (value<32 || value > 127)? UNKNOWN_CHARACTER: (char) value;


	                // After 15 bytes, reset everything for formatting purpose
	                if (countElements == 15) {
	                    result += (String.format("%08d: %-50s | %s\n",countRows*10, hex, input));
	                    countElements = 0;
	                    hex = input = "";
	                    countRows++;
	                } else {
	                    countElements++;
	                }

	            }
	            

	            // if the count>0, meaning there is remaining content
	            if (countElements > 0) {
	            	countRows++;
	            	result += (String.format("%08d: %-50s | %s\n",countRows*10, hex, input));
	            }

	        }

	        return result.toString();
	    }

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return COMMAND_DESCRIPTION;
	}

}
