import java.util.List;

public class Run20x1 extends Base {

    public static void main(String[] args) {
        new Run20x1().run();
    }

    @Override
    String doTheThing() throws Exception {
        int result = -1;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < getLines().size(); i++) {
            String line = getLines().get(i);
            List<String> parts = Util.getParts(line);
            String a = parts.get(parts.size() - 1);
            a = a.substring(a.indexOf('<') + 1, a.length() - 1);

            List<Integer> v = Util.getIntegerParts(a, ",");

            int value = v.stream().mapToInt(Math::abs).sum();

            if (value < min) {
                result = i;
                min = value;
            }
        }

        return String.valueOf(result);
    }
}
