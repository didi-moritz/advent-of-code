import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Run07x2 extends Base {

    public static void main(String[] args) {
        new Run07x2().run();
    }

    private static final int MAX = 65536;

    @Override
    String doTheThing() {
        Map<String, Integer> map = new HashMap<>();

        List<String> lines = new ArrayList<>(getLines());
        do {
            lines = execute(lines, map);
        } while (!lines.isEmpty());

        Integer a = map.get("a");
        map.clear();
        map.put("b", a);

        lines = getLines().stream().filter(l -> !l.endsWith(" b")).collect(Collectors.toList());
        do {
            lines = execute(lines, map);
        } while (!lines.isEmpty());

        return String.valueOf(map.get("a"));
    }

    private List<String> execute(List<String> lines, Map<String, Integer> map) {
        List<String> unused = new ArrayList<>();

        for (String l : lines) {
            List<String> parts = Util.getParts(l, " -> ");

            String to = parts.get(1);

            List<String> input = Util.getParts(parts.get(0));

            try {
                Integer v = null;
                if ("NOT".equals(input.get(0))) {
                    v = not(map, input.get(1));
                } else if (input.size() == 1) {
                    v = getValue(map, input.get(0));
                } else if ("AND".equals(input.get(1))) {
                    v = and(map, input.get(0), input.get(2));
                } else if ("OR".equals(input.get(1))) {
                    v = or(map, input.get(0), input.get(2));
                } else if ("LSHIFT".equals(input.get(1))) {
                    v = lshift(map, input.get(0), input.get(2));
                } else if ("RSHIFT".equals(input.get(1))) {
                    v = rshift(map, input.get(0), input.get(2));
                }

                if (v != null) {
                    map.put(to, v);
                }
            } catch (Exception e) {
                unused.add(l);
            }
        }

        return unused;
    }

    private Integer lshift(Map<String, Integer> map, String v1, String v2) throws Exception {
        return (getValue(map, v1) << getValue(map, v2)) % MAX;
    }

    private Integer rshift(Map<String, Integer> map, String v1, String v2) throws Exception {
        return getValue(map, v1) >>> getValue(map, v2);
    }

    private Integer and(Map<String, Integer> map, String v1, String v2) throws Exception {
        return getValue(map, v1) & getValue(map, v2);
    }

    private Integer or(Map<String, Integer> map, String v1, String v2) throws Exception {
        return getValue(map, v1) | getValue(map, v2);
    }

    private Integer not(Map<String, Integer> map, String value) throws Exception {
        return MAX - 1 - getValue(map, value);
    }

    private Integer getValue(Map<String, Integer> map, String value) throws Exception {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {}

        if (map.containsKey(value)) {
            return map.get(value);
        }

        throw new Exception();
    }
}