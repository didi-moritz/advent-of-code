import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class Base {

    private static String YEAR_PATH = "2017";

    private static String INPUT_FILENAME_PATTERN = YEAR_PATH + "/resources/input-%02d.txt";

    private static String TEST_INPUT_FILENAME_PATTERN = YEAR_PATH + "/resources/input-test-%02d.txt";
    private static String TEST_INPUT_FILENAME_PART_PATTERN = YEAR_PATH + "/resources/input-test-%02d-%d.txt";

    private static String RUN_TITLE_PATTERN = "# Running task for Day %d Part %d #";

    private static Pattern CLASS_NAME_PATTERN = Pattern.compile("^\\D+(\\d+)\\D(\\d)$");

	private int runNumber;

	private int partNumber;

	private String runCode;

	private List<String> inputFileLines;

	private List<String> testInputFileLines;

	private String commandLineInput = "";

    enum InputType {
        FILE, TEST_FILE, COMMAND_LINE
    }

    private InputType inputType = InputType.FILE;

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
            readTestInputFile();

            showMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMenu() throws Exception {
	    while (true) {
            System.out.println("############################");
            System.out.println("# (1) Use input file       #");
            if (this.testInputFileLines != null) {
                System.out.println("# (2) Use test input file  #");
            }
            System.out.println("# (3) Enter input          #");
            System.out.println("############################");
            System.out.print("# Enter choice: ");

            String choice = readConsoleInput();

            if (choice == null) {
                break;
            }

            switch (choice) {
                case "1":
                    setInputType(InputType.FILE);
                    doAndPrintTheThing();
                    break;
                case "2":
                    if (this.testInputFileLines != null) {
                        setInputType(InputType.TEST_FILE);
                        doAndPrintTheThing();
                    }
                    break;
                case "3":
                    enterInput();
                    break;
            }
        }
    }

    private void enterInput() throws Exception {
	    System.out.print("# Enter input: ");

	    this.commandLineInput = readConsoleInput();

	    if (this.commandLineInput == null) {
	        return;
        }

        setInputType(InputType.COMMAND_LINE);

	    doAndPrintTheThing();

        enterInput();
    }

    private String readConsoleInput() {
	    try {
            Scanner scanner = new Scanner(System.in);
            String result = scanner.nextLine().trim();
            return result.isEmpty() ? null : result;
        } catch (NoSuchElementException e) {
	        return null;
        }
    }

    private void doAndPrintTheThing() throws Exception {
        printResult(doTheThing());
    }

    private void printResult(String result) {
	    System.out.println("# Result: " + result);
    }

    private void readInputFile() {
        inputFileLines = readFile(String.format(INPUT_FILENAME_PATTERN, runNumber));
	}

    private void readTestInputFile() {
        testInputFileLines = readFile(
                String.format(TEST_INPUT_FILENAME_PART_PATTERN, runNumber, partNumber),
                false);

        if (testInputFileLines == null) {
            testInputFileLines = readFile(
                    String.format(TEST_INPUT_FILENAME_PATTERN, runNumber),
                    false);
        }
	}

	private List<String> readFile(String filePath) {
        return readFile(filePath, true);
    }

	private List<String> readFile(String filePath, boolean printError) {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            return stream
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            if (printError) {
                System.err.println("Cannot read " + filePath);
            }
            return null;
        }
    }

	private void setCommandLineInput(String input) {
	    this.commandLineInput = input;
    }

    private void setInputType(InputType inputType) {
	    this.inputType = inputType;
    }

    List<String> getLines() {
        switch (inputType) {
            case FILE:
                return inputFileLines;
            case TEST_FILE:
                return testInputFileLines;
            default:
            case COMMAND_LINE:
                return Collections.singletonList(commandLineInput);
        }
    }

    String getInput() {
        return getLines().stream().collect(Collectors.joining("\r\n")).trim();
    }

    List<String> getInputStrings() {
        return getInputStrings("\\s+");
    }

    List<String> getInputStrings(String regex) {
	    return Arrays.asList(getInput().split(regex));
    }

    List<Integer> getInputIntegers() {
	    return getInputStrings().stream().map(Integer::valueOf).collect(Collectors.toList());
    }

    abstract String doTheThing() throws Exception;
}