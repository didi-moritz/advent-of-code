import java.util.*;

public class Run18x2 extends Base {

    public static void main(String[] args) {
        new Run18x2().run();
    }


    private List<String> lines;

    @Override
    String doTheThing() {
        lines = getLines();

        List<Program> programs = Arrays.asList(new Program(0), new Program(1));

        programs.get(0).setOtherProgram(programs.get(1));
        programs.get(1).setOtherProgram(programs.get(0));

        boolean end = false;
        do {

            int r0 = programs.get(0).runUntilNoRcvAvailabe();
            int r1 = programs.get(1).runUntilNoRcvAvailabe();

            if (r0 < 1 && r1 < 1) {
                end = true;
            }

        } while (!end);

        return String.valueOf(programs.get(1).getResult());
    }

    private class Program {

        private long id;

        private Map<String, Long> map;

        private int pos;

        private List<Long> sends;

        private Program otherProgram;

        private int result = 0;

        Program(long p) {
            id = p;

            map = new HashMap<>();

            map.put("p", p);

            pos = 0;

            sends = new ArrayList<>();
        }

        void setOtherProgram(Program otherProgram) {
            this.otherProgram = otherProgram;
        }

        long getRcv() {
            if (sends.isEmpty()) {
                return -1;
            }

            return sends.remove(0);
        }

        public int getResult() {
            return result;
        }

        private int runUntilNoRcvAvailabe() {
            System.out.println("Running " + id);

            int runs = 0;
            while (pos < lines.size()) {
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
                        if (!rcv(x)) {
                            return runs;
                        }
                        break;
                    case "jgz":
                        jumped = jgz(getValue(x), y);
                        break;
                }

                if (!jumped) {
                    pos++;
                }

                runs++;
            }

            return -1;
        }

        private boolean jgz(long x, long v) {
            if (x <= 0) {
                return false;
            }

            pos += v;
            return true;
        }

        private boolean rcv(String x) {
            long v = otherProgram.getRcv();

            if (v >= 0) {
                map.put(x, v);
                return true;
            }

            return false;
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
            result++;
            sends.add(x);
        }

        private long getValue(String a) {
            try {
                return Long.parseLong(a);
            } catch (NumberFormatException e) {
                return map.getOrDefault(a, 0L);
            }
        }
    }
}
