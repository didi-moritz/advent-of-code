import java.util.List;
import java.util.stream.Collectors;

class Run08x2 extends Base {

    public static void main(String[] args) {
        new Run08x2().run();
    }

    @Override
    String doTheThing() {
        List<String> finalLines = getLines().stream()
                .map(s -> s
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replaceAll("^|$", "\""))
                .collect(Collectors.toList());

        finalLines.forEach(System.out::println);

        int finalCount = finalLines.stream()
                .mapToInt(String::length)
                .sum();

        int originalCount = getLines().stream()
                .mapToInt(String::length)
                .sum();

        System.out.println("originalCount: " + originalCount);
        System.out.println("finalCount: " + finalCount);
        System.out.println("sum: " + (finalCount - originalCount));


        return String.valueOf(finalCount - originalCount);
    }
}