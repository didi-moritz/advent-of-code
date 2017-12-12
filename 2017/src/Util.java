import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
    static List<String> getParts(String line) {
        return getParts(line, "\\s+");
    }

    static List<String> getParts(String line, String regex) {
        return Arrays.asList(line.split(regex));
    }

    static List<Integer> getIntegerParts(String line) {
        return getIntegerParts(line, "\\s+");
    }

    static List<Integer> getIntegerParts(String line, String regex) {
        return getParts(line, regex).stream().map(Integer::valueOf).collect(Collectors.toList());
    }
}
