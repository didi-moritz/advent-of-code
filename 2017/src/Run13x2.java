import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Run13x2 extends Base {

    public static void main(String[] args) {
        new Run13x2().run();
    }


    @Override
    String doTheThing() {
        Map<Integer, Integer> map = new HashMap<>();

        for (String l : getLines()) {
            List<Integer> p = Util.getIntegerParts(l, ": ");
            map.put(p.get(0), p.get(1));
        }

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            final int offset = i;
            if (map.entrySet().stream()
                    .noneMatch(e -> {
                        int pos = e.getKey();
                        int v = e.getValue();

                        int mod = (v - 1) * 2;

                        return ((pos + offset) % mod == 0);
                    })) {
                return String.valueOf(i);
            }
        }

        return "-1";
    }
}