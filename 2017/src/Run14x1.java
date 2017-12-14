import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class Run14x1 extends Base {

    public static void main(String[] args) {
        new Run14x1().run();
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

        return String.valueOf(bits.stream()
                .map(b -> b.replace("0", ""))
                .mapToInt(String::length)
                .sum());
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