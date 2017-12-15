import java.util.List;

class Run01x2 extends Base {

    public static void main(String[] args) {
        new Run01x2().run();
    }

    @Override
    String doTheThing() {
        int result = 0;

        List<Integer> list = getInputIntegers("");

        for (int i = 0; i < list.size() / 2; i++) {
            result += check(list.get(i), list.get((i + (list.size() / 2)) % list.size()));
        }

        return Integer.toString(result * 2);
    }

    private int check(int a, int b) {
        return a == b ? a : 0;
    }

}