import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Run11x2 extends Base {

    public static void main(String[] args) {
        new Run11x2().run();
    }

    private static final List<Opposites> OPPOSITES_LIST = Arrays.asList(
            new Opposites("n", "s"),
            new Opposites("ne", "sw"),
            new Opposites("nw", "se"));

    private static final List<Merges> MERGES_LIST = Arrays.asList(
            new Merges("ne", "nw", "n"),
            new Merges("n", "se", "ne"),
            new Merges("n", "sw", "nw"),
            new Merges("se", "sw", "s"),
            new Merges("s", "ne", "se"),
            new Merges("s", "nw", "sw"));

    private static class Opposites {
        String a;
        String b;

        public Opposites(String a, String b) {
            this.a = a;
            this.b = b;
        }
    }

    private static class Merges {
        String a;
        String b;
        String c;

        public Merges(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    @Override
    String doTheThing() throws Exception {
        List<String> dirs = getInputStrings(",");

        Map<String, Integer> counts = new HashMap<>();

        int result = 0;

        for (String dir : dirs) {
            Integer currentValue = counts.getOrDefault(dir,0);
            counts.put(dir, ++currentValue);

            result = Math.max(getStepCount(new HashMap<>(counts)), result);
        }

        return String.valueOf(result);
    }

    private int getStepCount(Map<String, Integer> counts) {
        for (Opposites opposites : OPPOSITES_LIST) {
            int a = counts.getOrDefault(opposites.a, 0);
            int b = counts.getOrDefault(opposites.b, 0);

            if (a > b) {
                a -= b;
                b = 0;
            } else {
                b -= a;
                a = 0;
            }

            counts.put(opposites.a, a);
            counts.put(opposites.b, b);
        }

        for (Merges merges: MERGES_LIST) {
            int a = counts.getOrDefault(merges.a, 0);
            int b = counts.getOrDefault(merges.b, 0);
            int c = counts.getOrDefault(merges.c, 0);

            if (a > b) {
                a -= b;
                c += b;
                b = 0;
            } else {
                b -= a;
                c += a;
                a = 0;
            }

            counts.put(merges.a, a);
            counts.put(merges.b, b);
            counts.put(merges.c, c);
        }

        return counts.values().stream().mapToInt(Integer::valueOf).sum();
    }
}