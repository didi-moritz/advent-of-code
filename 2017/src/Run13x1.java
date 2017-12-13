import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Run13x1 extends Base {

    public static void main(String[] args) {
        new Run13x1().run();
    }


    @Override
    String doTheThing() {
        Map<Integer, Integer> map = new HashMap<>();

        for (String l : getLines()) {
            List<Integer> p = Util.getIntegerParts(l, ": ");
            map.put(p.get(0), p.get(1));
        }

        int sum = map.entrySet().stream()
                .filter(e -> {
                    int pos = e.getKey();
                    int v = e.getValue();

                    int mod = (v - 1) * 2;

                    return (pos % mod == 0);
                })
                .mapToInt(e -> e.getKey() * e.getValue())
                .sum();

        return String.valueOf(sum);
    }
}