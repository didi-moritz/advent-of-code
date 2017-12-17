import java.util.ArrayList;
import java.util.List;

public class Run17x1 extends Base {

    public static void main(String[] args) {
        new Run17x1().run();
    }

    @Override
    String doTheThing() {
        int skip = Integer.parseInt(getInput());

        List<Integer> ints = new ArrayList<>();

        ints.add(0);

        int pos = 0;

        for (int i = 0; i < 2017; i++) {
            pos += skip;
            pos %= ints.size();

            ints.add(++pos, i + 1);
        }

        pos++;
        pos %= ints.size();

        return String.valueOf(ints.get(pos));
    }
}
