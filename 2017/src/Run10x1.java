import java.util.List;

class Run10x1 extends Base {

    public static void main(String[] args) {
        new Run10x1().run();
    }

    @Override
    String doTheThing() throws Exception {
        List<String> lengths = getInputStrings(",");

        int[] a = new int[256];

        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }

        int pos = 0;
        int skip = 0;

        for (String lengthText : lengths) {
            int length = Integer.parseInt(lengthText);

            for (int i = 0; i < length / 2; i++) {
                int i1 = (pos + i) % a.length;
                int i2 = (pos + length - i - 1) % a.length;

                switchA(a, i1, i2);
            }

            pos += length + skip++;
            pos %= a.length;
        }

        return String.valueOf(a[0] * a[1]);
    }

    private void switchA(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

}