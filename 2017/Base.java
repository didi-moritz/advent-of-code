import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class Base {

    private static String YEAR_PATH = "2017";

    private static String INPUT_FILENAME_PATTERN = YEAR_PATH + "/input-%02d.txt";

    private static String RUN_TITLE_PATTERN = "# Running task for Day %d Part %d #";

    private static Pattern CLASS_NAME_PATTERN = Pattern.compile("^\\D+(\\d+)\\D(\\d)$");

	private int runNumber;

	private int partNumber;

	private String runCode;

	private List<String> inputFileLines;

	private List<String> testFileInputLines;

	private String commandLineInput = "";

    enum INPUT_TYPE {
        FILE, TEST_FILE, COMMAND_LINE
    }

    private INPUT_TYPE inputType = INPUT_TYPE.FILE;

	void run() {
        try {
            String className = this.getClass().getSimpleName();
            Matcher matcher = CLASS_NAME_PATTERN.matcher(className);
            if (!matcher.matches()) {
                throw new Exception("Class name " + className + " doesn't match pattern " + CLASS_NAME_PATTERN);
            }
            int runNumber = Integer.parseInt(matcher.group(1));
            int partNumber = Integer.parseInt(matcher.group(2));

            this.runNumber = runNumber;
            this.partNumber = partNumber;

            this.runCode = String.format("%02d.%d", runNumber, partNumber);

            System.out.println(String.format(RUN_TITLE_PATTERN, runNumber, partNumber));

            readInputFile();


            printResult(doTheThing());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printResult(String result) {
	    System.out.println("# Result: " + result);
    }

    private void readInputFile() {
		String filePath = String.format(INPUT_FILENAME_PATTERN, runNumber);

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            inputFileLines = stream
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Cannot read " + filePath);
        }
	}

	private void setCommandLineInput(String input) {
	    this.commandLineInput = input;
    }

    List<String> getLines() {
        switch (inputType) {
            case FILE:
                return inputFileLines;
            case TEST_FILE:
                return testFileInputLines;
            default:
            case COMMAND_LINE:
                return Collections.singletonList(commandLineInput);
        }
    }

    String getInput() {
        return getLines().stream().collect(Collectors.joining("\r\n")).trim();
    }

    List<String> getInputStrings() {
	    return Arrays.asList(getInput().split("\\s+"));
    }

    List<Integer> getInputIntegers() {
	    return getInputStrings().stream().map(Integer::valueOf).collect(Collectors.toList());
    }

    abstract String doTheThing() throws Exception;
}