import java.util.List;

class Run15x2 extends Base {

    public static void main(String[] args) {
        new Run15x2().run();
    }


    private static long[] F = {16807, 48271};
    private static long[] CHECK_MOD = {4, 8};

    private static long MOD = 2147483647;

    private long[] v = {0, 0};

    @Override
    String doTheThing() {

        List<String> parts;
        for (int i = 0; i < 2; i++) {
            parts = Util.getParts(getLines().get(i));
            v[i] = Integer.parseInt(parts.get(parts.size() - 1));
        }

        int result = 0;
        for (int i = 0; i < 5_000_000; i++) {
            moveToNextValidPair();

            String[] s = new String[2];

            for (int j = 0; j < 2; j++) {
                s[j] = Long.toUnsignedString(v[j], 2);
                if (s[j].length() > 16) {
                    s[j] = s[j].substring(s[j].length() - 16, s[j].length());
                }
            }

            if (s[0].equals(s[1])) {
                result++;
            }

            if ((i + 1) % 100_000 == 0) {
                System.out.println((i + 1) + " done");
            }
        }

        return String.valueOf(result);
    }

    private void moveToNextValidPair() {
        for (int i = 0; i < 2; i++) {
            do {
                v[i] *= F[i];
                v[i] %= MOD;
            } while(v[i] % CHECK_MOD[i] != 0);
        }
    }
}