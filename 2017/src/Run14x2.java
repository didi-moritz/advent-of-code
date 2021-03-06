import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class Run14x2 extends Base {

    public static void main(String[] args) {
        new Run14x2().run();
    }

    private static List<Integer> LENGTHS_SUFFIX = Arrays.asList(17, 31, 73, 47, 23);

    @Override
    String doTheThing() {

        String baseInput = getInput();

        List<String> bits = new ArrayList<>();

        for (int i = 0; i < 128; i++) {
            List<Byte> foo = getKnotHash(baseInput + "-" + i);

            bits.add(foo.stream()
                    .map(b -> Integer.toUnsignedString(b, 2))
                    .map(s1 -> s1.length() > 8 ? s1.substring(s1.length() - 8, s1.length()) : s1)
                    .map(s2 -> {
                        StringBuilder sb = new StringBuilder(s2);
                        while (sb.length() < 8) {
                            sb.insert(0, "0");
                        }
                        return sb.toString();
                    })
                    .collect(Collectors.joining()));
        }

        boolean[][] realBits = new boolean[bits.size()][bits.get(0).length()];

        for (int i = 0; i < bits.size(); i++) {
            String l = bits.get(i);
            for (int j = 0; j < l.length(); j++) {
                realBits[i][j] = "1".equals(l.substring(j, j + 1));
            }
        }

        int result = 0;
        while (true) {
            int x = -1;
            int y = -1;
            for (int i = 0; x < 0 && i < realBits.length; i++) {
                for (int j = 0; x < 0 && j < realBits[i].length; j++) {
                    if (realBits[i][j]) {
                        x = i;
                        y = j;
                    }
                }
            }

            if (x == -1) {
                break;
            }

            markBit(x, y, realBits);

            result++;
        }

        return String.valueOf(result);
    }

    private void markBit(int x, int y, boolean[][] bits) {
        if (x < 0 || y < 0) {
            return;
        }

        if (x >= bits.length || y >= bits[x].length) {
            return;
        }

        if (!bits[x][y]) {
            return;
        }

        bits[x][y] = false;

        markBit(x + 1, y, bits);
        markBit(x - 1, y, bits);
        markBit(x, y + 1, bits);
        markBit(x, y - 1, bits);
    }

    private List<Byte> getKnotHash(String input) {
        List<Integer> lengths = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            lengths.add((int) input.charAt(i));
        }

        lengths.addAll(LENGTHS_SUFFIX);

        int[] a = new int[256];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }

        AtomicInteger pos = new AtomicInteger(0);
        AtomicInteger skip = new AtomicInteger(0);

        for (int i = 0; i < 64; i++) {
            runLengths(a, lengths, pos, skip);
        }

        List<Byte> bytes = new ArrayList<>();

        for (int i = 0; i < 16; i++) {
            int r = a[i * 16];
            for (int j = 1; j < 16; j++) {
                r ^= a[i * 16 + j];
            }

            byte[] foo = ByteBuffer.allocate(4).putInt(r).array();
            bytes.add(foo[3]);
        }

        return bytes;
    }

    private void runLengths(int[] a, List<Integer> lengths, AtomicInteger pos, AtomicInteger skip) {
        for (Integer length : lengths) {
            for (int i = 0; i < length / 2; i++) {
                int i1 = (pos.get() + i) % a.length;
                int i2 = (pos.get() + length - i - 1) % a.length;

                switchA(a, i1, i2);
            }

            pos.set((pos.get() + length + skip.get()) % a.length);
            skip.incrementAndGet();
        }
    }

    private void switchA(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

}