import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Run01x1 extends Base {

    public static void main(String[] args) {
        new Run01x1().run(1, 1);
    }

    @Override
    String doTheThing() {
        int result = 0;

        List<Integer> list = Arrays.stream(getInput().split("")).map(Integer::valueOf).collect(Collectors.toList());

        for (int i = 0; i < list.size(); i++) {
            result += check(list.get(i), list.get((i + 1) % list.size()));
        }

        return Integer.toString(result);
    }

    private int check(int a, int b) {
        return a == b ? a : 0;
    }

}