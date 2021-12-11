package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellParser.ParsingMode;
import hr.fer.zemris.java.hw06.shell.ShellParser.Token;

public class TreeCommand implements ShellCommand {

	private static final String COMMAND_NAME = "tree";
	private static final List<String> COMMAND_DESCRIPTION = List.of(
			"Graphically displays the folder structure of a drive or path.", 
			"tree [directory] ", 
			"\r\r directory - directory name to print tree.");
	
	private List<Token> tokens;
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		tokens = new ShellParser(arguments, ParsingMode.ARGUMENTS_PARSING).getTokens();

		if (tokens.size() != 1) {
			env.writeln("Invalid number of arguments for method symbol should be max 1, but was: " + tokens.size());
			return ShellStatus.CONTINUE;
		}
		
		String path = tokens.get(0).getValue().startsWith("\"") ? tokens.get(0).getValue().replace("\"", "")
				: tokens.get(0).getValue();
		
		File directory = new File(path);
		
		if (directory.isDirectory()) {
			env.write(renderFolder(new File(path), 0, new StringBuilder(), false).toString());
		} else {
			env.writeln("Given path is not direcotry!");
		}

		
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return COMMAND_DESCRIPTION;
	}
	
	
	
	
	private static StringBuilder renderFolder(File folder, int level, StringBuilder sb, boolean isLast) {
      indent(sb, level, isLast).append("[DIR] "+folder.getName()+"\n");

      File[] objectsInDir = folder.listFiles();

      for (int i = 0; i < objectsInDir.length; i++) {
        boolean last = ((i + 1) == objectsInDir.length);

        if (objectsInDir[i].isDirectory()) {
          renderFolder(objectsInDir[i], level + 1, sb, last);
        } else {
          renderFile(objectsInDir[i], level + 1, sb, last);
        }
      }

      return sb;
    }

    private static StringBuilder renderFile(File file, int level, StringBuilder sb, boolean isLast) {
      return indent(sb, level, isLast).append("── "+file.getName()+"\n");
    }

    private static StringBuilder indent(StringBuilder sb, int level, boolean isLast) {
        sb.append("|  ".repeat(level));

      if (level > 0) {
        sb.append(isLast? "├─": "├─");
      }

      return sb;
    }

    
}
	



