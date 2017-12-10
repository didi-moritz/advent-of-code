import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class Run10x2 extends Base {

    public static void main(String[] args) {
        new Run10x2().run();
    }

    private static List<Integer> LENGTHS_SUFFIX = Arrays.asList(17, 31, 73, 47, 23);

    @Override
    String doTheThing() throws Exception {
        List<String> chars = getInputStrings("");

        List<Integer> lengths = new ArrayList<>();

        for (String c : chars) {
            lengths.add((int) c.charAt(0));
        }

        lengths.addAll(LENGTHS_SUFFIX);

        System.out.println(lengths.stream().map(String::valueOf).collect(Collectors.joining(",")));

        int[] a = new int[256];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }

        AtomicInteger pos = new AtomicInteger(0);
        AtomicInteger skip = new AtomicInteger(0);

        for (int i = 0; i < 64; i++) {
            runLengths(a, lengths, pos, skip);
        }

        int[] b = new int[16];

        for (int i = 0; i < 16; i++) {
            int r = a[i * 16];
            for (int j = 1; j < 16; j++) {
                r ^= a[i * 16 + j];
            }
            b[i] = r;
        }

        System.out.println(Arrays.stream(b).mapToObj(String::valueOf).collect(Collectors.joining(",")));

        return Arrays.stream(b)
                .mapToObj(Integer::toHexString)
                .map(s -> s.length() == 1 ? "0" + s : s)
                .collect(Collectors.joining(""));
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