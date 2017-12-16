import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Run16x1 extends Base {

    public static void main(String[] args) {
        new Run16x1().run();
    }

    private List<Character> chars;

    @Override
    String doTheThing() {
        chars = new ArrayList<>();

        for (int i = 0; i < 16; i++) {
            chars.add((char) ('a' + i));
        }

        List<String> commands = getInputStrings(",");
        for (String command : commands) {
            char type = command.charAt(0);

            switch (type) {
                case 's':
                    spin(Integer.parseInt(command.substring(1, command.length())));
                    break;
                case 'x': {
                    List<Integer> pair = Util.getIntegerParts(command.substring(1), "/");
                    exchange(pair.get(0), pair.get(1));
                    break;
                }
                case 'p': {
                    List<String> pair = Util.getParts(command.substring(1), "/");
                    partner(pair.get(0).charAt(0), pair.get(1).charAt(0));
                    break;
                }
            }
        }

        return chars.stream()
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    private void partner(char a, char b) {
        int posA = chars.indexOf(a);
        int posB = chars.indexOf(b);
        exchange(posA, posB);
    }

    private void exchange(int a, int b) {
        char temp = chars.get(a);
        chars.set(a, chars.get(b));
        chars.set(b, temp);
    }

    private void spin(int i) {
        List<Character> newInts = chars.subList(chars.size() - i, chars.size());
        newInts.addAll(chars.subList(0, chars.size() - i));

        chars = newInts;
    }
}
