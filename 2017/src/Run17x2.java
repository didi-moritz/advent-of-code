public class Run17x2 extends Base {

    public static void main(String[] args) {
        new Run17x2().run();
    }

    @Override
    String doTheThing() {
        int skip = Integer.parseInt(getInput());

        int pos = 0;

        int result = -1;

        for (int i = 0; i < 50_000_000; i++) {
            pos += skip;
            pos %= i + 1;

            if (pos == 0) {
                result = i + 1;
            }

            pos++;
        }

        return String.valueOf(result);
    }
}
