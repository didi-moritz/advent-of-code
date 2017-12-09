import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Run06x2 extends Base {

    public static void main(String[] args) {
        new Run06x2().run();
    }

    enum Type {
        ON, OFF, TOGGLE;

        static Type byNames(String one, String two) {
            if ("turn".equals(one)) {
                if ("on".equals(two)) {
                    return ON;
                }
                return OFF;
            }
            return TOGGLE;
        }
    }

    @Override
    String doTheThing() {
        int[][] lights = new int[1000][1000];

        for (String line : getLines()) {
            List<String> parts = Util.getParts(line);

            Type type = Type.byNames(parts.get(0), parts.get(1));

            int offset = type == Type.TOGGLE ? 1 : 2;

            List<Integer> from = Arrays.stream(parts.get(offset).split(",")).map(Integer::valueOf).collect(Collectors.toList());
            List<Integer> to = Arrays.stream(parts.get(offset + 2).split(",")).map(Integer::valueOf).collect(Collectors.toList());

            for (int x = from.get(0); x <= to.get(0); x++) {
                for (int y = from.get(1); y <= to.get(1); y++) {
                    switch (type) {
                        case ON:
                            lights[x][y]++;
                            break;
                        case OFF:
                            if (lights[x][y] > 0) {
                                lights[x][y]--;
                            }
                            break;
                        case TOGGLE:
                            lights[x][y] += 2;
                            break;
                    }
                }
            }

        }

        int result = 0;

        for (int[] ll : lights) {
            for (int l : ll) {
                result += l;
            }
        }

        return String.valueOf(result);
    }
}