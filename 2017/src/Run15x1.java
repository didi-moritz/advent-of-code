import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class Run15x1 extends Base {

    public static void main(String[] args) {
        new Run15x1().run();
    }


    private static long F1 = 16807;
    private static long F2 = 48271;

    private static long MOD = 2147483647;

    @Override
    String doTheThing() {

        List<String> parts;
        parts = Util.getParts(getLines().get(0));
        long v1 = Integer.parseInt(parts.get(parts.size() - 1));

        parts = Util.getParts(getLines().get(1));
        long v2 = Integer.parseInt(parts.get(parts.size() - 1));

        int result = 0;
        for (int i = 0; i < 40_000_000; i++) {
            v1 *= F1;
            v1 %= MOD;

            v2 *= F2;
            v2 %= MOD;

            String s1 = Long.toUnsignedString(v1, 2);
            if (s1.length() > 16) {
                s1 = s1.substring(s1.length() - 16, s1.length());
            }

            String s2 = Long.toUnsignedString(v2, 2);
            if (s2.length() > 16) {
                s2 = s2.substring(s2.length() - 16, s2.length());
            }

            if (s1.equals(s2)) {
                result++;
            }

            if ((i + 1) % 100_000 == 0) {
                System.out.println((i + 1) + " done");
            }
        }

        return String.valueOf(result);
    }
}