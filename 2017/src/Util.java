import java.util.Arrays;
import java.util.List;

public class Util {
    static List<String> getParts(String line) {
        return Arrays.asList(line.split("\\s+"));
    }
}
