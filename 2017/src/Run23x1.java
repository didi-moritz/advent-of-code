import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Run23x1 extends Base {

    public static void main(String[] args) {
        new Run23x1().run();
    }

    private Map<String, Long> map;

    private int pos;

    @Override
    String doTheThing() {
        map = new HashMap<>();

        pos = 0;

        int result = 0;

        List<String> lines = getLines();

        do {
            String line = lines.get(pos);

            System.out.println(pos + " - " + line);

            List<String> parts = Util.getParts(line);

            String cmd = parts.get(0);
            String x = parts.get(1);
            long y = getValue(parts.get(2));

            boolean jumped = false;

            switch (cmd) {
                case "set":
                    set(x, y);
                    break;
                case "sub":
                    add(x, -y);
                    break;
                case "mul":
                    mul(x, y);
                    result++;
                    break;
                case "jnz":
                    jumped = jnz(getValue(x), y);
                    break;
            }

            if (!jumped) {
                pos++;
            }

        } while (pos < lines.size());

        return String.valueOf(result);
    }

    private boolean jnz(long x, long y) {
        if (x == 0) {
            return false;
        }

        pos += y;
        return true;
    }

    private void mul(String x, long y) {
        map.put(x, getValue(x) * y);
    }

    private void add(String x, long y) {
        long v = getValue(x) + y;
        map.put(x, v);
    }

    private void set(String x, long y) {
        map.put(x, y);
    }

    private long getValue(String a) {
        try {
            return Long.parseLong(a);
        } catch (NumberFormatException e) {
            return map.getOrDefault(a, 0L);
        }
    }
}
