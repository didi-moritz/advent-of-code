import java.util.Arrays;
import java.util.List;

class Run05x1 extends Base {

    public static void main(String[] args) {
        new Run05x1().run();
    }

    @Override
    String doTheThing() {
        int result = 0;

        String vowelText = "aeiou";
        List<String> vowels = Arrays.asList(vowelText.split(""));
        List<String> uglyParts = Arrays.asList("ab", "cd", "pq", "xy");

        for (String l : getLines()) {
            List<String> chars = Arrays.asList(l.split(""));

            boolean containUglyPart = false;
            for (String uglyPart : uglyParts) {
                if (l.contains(uglyPart)) {
                    containUglyPart = true;
                    break;
                }
            }
            if (containUglyPart) {
                continue;
            }

            int vowelCnt = 0;
            boolean hasDouble = false;

            for (int i = 0; i < chars.size(); i++) {
                String c = chars.get(i);

                if (vowels.contains(c)) {
                    vowelCnt++;
                }

                if (i > 0 && c.equals(chars.get(i - 1))) {
                    hasDouble = true;
                }
            }

            if (vowelCnt >= 3 && hasDouble) {
                System.out.println(l);
                result++;
            }
        }

        return String.valueOf(result);
    }
}