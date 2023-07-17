package app.resketchware.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Scanner;

public class BinaryExecutor {

    private final ProcessBuilder processBuilder = new ProcessBuilder();
    private final StringWriter logWriter = new StringWriter();

    public void setCommands(List<String> commands) {
        processBuilder.command(commands);
    }

    public String execute() {
        try {
            Process process = processBuilder.start();
            Scanner scanner = new Scanner(process.getErrorStream());
            while (scanner.hasNextLine()) {
                logWriter.append(scanner.nextLine());
                logWriter.append(System.lineSeparator());
            }
            process.waitFor();
        } catch (Exception e) {
            logWriter.write(e.getMessage());
        }
        return logWriter.toString();
    }

    public String getLog() {
        return logWriter.toString();
    }
}