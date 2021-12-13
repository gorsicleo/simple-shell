package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**Class that contains useful methods for executing shell commands.
 * @author gorsicleo
 *
 */
public class CommandUtil {

	private static final String NULL_ERROR_MESSAGE = "Cannot parse empty argument";
	private static final String UNKNOWN_CHARACTER = ".";

	/**Method removes quotation marks from beginning and end of string
	 * @param src quoted string
	 * @return src without ""
	 */
	public static String removeQuotation(String src) {
		if (src == null) return NULL_ERROR_MESSAGE;
		return src.startsWith("\"") ? src.replace("\"", "") : src;
	}

	/**Method returns formatted hexdump for given path
	 * @param path to display hexdump
	 * @return hexdump in format of row | hex | char
	 * @throws IOException
	 */
	public static String convertFileToHexDump(Path path) throws IOException {

		String result = new String();
		String hex = new String();
		String input = new String();

		int countElements = 0;
		int countRows = 0;
		int value;

		try (InputStream inputStream = Files.newInputStream(path)) {

			while ((value = inputStream.read()) != -1) {
				hex += (String.format("%02X ", value));
				input += (value < 32 || value > 127) ? UNKNOWN_CHARACTER : (char) value;


				if (countElements == 15) {
					result += (String.format("%08d: %-50s | %s\n", countRows * 10, hex, input));
					countElements = 0;
					hex = input = "";
					countRows++;
				} else {
					countElements++;
				}

			}
			if (countElements > 0) {
				countRows++;
				result += (String.format("%08d: %-50s | %s\n", countRows * 10, hex, input));
			}
		}
		
		return result.toString();
	}

	/**Method returns string representing file attributes 
	 * r - readable , w - writable , x - executable, d - directory
	 * 
	 * besides that creation time is also returned as well as size and filename
	 * @param file to print attributes for
	 * @return file attributes (rwxd | creation time | filename)
	 * @throws IOException
	 */
	public static String printFileAttrs(File folder) throws IOException {
		File[] objectsInDir = folder.listFiles();
		String allFilesAttrs = new String();
		for (File object : objectsInDir) {
			String permissions = "";
			permissions += object.isDirectory() ? "d" : "-";
			permissions += object.canRead() ? "r" : "-";
			permissions += object.canWrite() ? "w" : "-";
			permissions += object.canExecute() ? "x" : "-";
			long fileSize = object.length();
			String creationTime = Files.getAttribute(object.toPath(), "creationTime").toString().replace('T', ' ');
			String fileName = object.getName();
			allFilesAttrs += String.format("%-5s %7d %-20s %s \n", permissions, fileSize, creationTime.substring(0, 19),
					fileName);

		}
		return allFilesAttrs;
	}

	/**Terminates current command and continues shell */
	public static ShellStatus terminateNotFatal(String message, Environment env) {
		env.writeln(message);
		return ShellStatus.CONTINUE;
	}

	/**Terminates current command and terminates shell */
	public static ShellStatus terminateFatal(String message, Environment env) {
		env.writeln(message);
		return ShellStatus.TERMINATE;
	}
}
