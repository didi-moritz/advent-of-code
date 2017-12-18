import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Run18x1 extends Base {

    public static void main(String[] args) {
        new Run18x1().run();
    }

    private Map<String, Long> map;

    private long playedSound;
    private boolean recovered;
    private int pos;

    @Override
    String doTheThing() {
        map = new HashMap<>();

        playedSound = -1;
        recovered = false;
        pos = 0;

        List<String> lines = getLines();

        do {
            String line = lines.get(pos);

            System.out.println(pos + " - " + line);

            List<String> parts = Util.getParts(line);

            String cmd = parts.get(0);
            String x = parts.get(1);
            long y = parts.size() == 3 ? getValue(parts.get(2)) : -1;

            boolean jumped = false;

            switch (cmd) {
                case "snd":
                    snd(getValue(x));
                    break;
                case "set":
                    set(x, y);
                    break;
                case "add":
                    add(x, y);
                    break;
                case "mul":
                    mul(x, y);
                    break;
                case "mod":
                    mod(x, y);
                    break;
                case "rcv":
                    rcv(getValue(x));
                    break;
                case "jgz":
                    jumped = jgz(getValue(x), y);
                    break;
            }

            if (!jumped) {
                pos++;
            }

        } while (!recovered);

        return String.valueOf(playedSound);
    }

    private boolean jgz(long x, long v) {
        if (x <= 0) {
            return false;
        }

        pos += v;
        return true;
    }

    private void rcv(long v) {
        if (v != 0) {
            recovered = true;
        }
    }

    private void mod(String x, long y) {
        map.put(x, getValue(x) % y);
    }

    private void mul(String x, long y) {
        map.put(x, getValue(x) * y);
    }

    private void add(String x, long y) {
        long v = getValue(x) + y;
        map.put(x, v < 0 ? 0 : v);
    }

    private void set(String x, long y) {
        map.put(x, y);
    }

    private void snd(long x) {
        playedSound = x;
    }


    private long getValue(String a) {
        try {
            return Long.parseLong(a);
        } catch (NumberFormatException e) {
            return map.getOrDefault(a, 0L);
        }
    }
}
