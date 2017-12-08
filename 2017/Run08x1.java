import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Run08x1 extends Base {

    public static void main(String[] args) {
        new Run08x1().run(8, 1);
    }

    @Override
    String doTheThing() throws Exception {
        Map<String, Integer> map = new HashMap<>();

        for (String l : getLines()) {
            List<String> parts = Util.getParts(l);

            String name = parts.get(0);
            boolean increase = "inc".equals(parts.get(1));
            int value = Integer.valueOf(parts.get(2));

            String checkName = parts.get(4);
            String checkOperator = parts.get(5);
            int checkValue = Integer.valueOf(parts.get(6));

            if (check(map, checkName, checkOperator, checkValue)) {
                increaseValue(map, name, increase ? value : -value);
            }
        }

        return String.valueOf(
                map.values().stream()
                        .mapToInt(Integer::valueOf)
                        .max()
                        .orElse(-1));
    }

    private boolean check(Map<String, Integer> map, String name, String operator, int value) throws Exception {
        int currentValue = getValue(map, name);

        switch (operator) {
            case ">":
                return currentValue > value;
            case ">=":
                return currentValue >= value;
            case "<":
                return currentValue < value;
            case "<=":
                return currentValue <= value;
            case "==":
                return currentValue == value;
            case "!=":
                return currentValue != value;
            default:
                throw new Exception("WTF means " + operator + "?");
        }
    }

    private int getValue(Map<String, Integer> map, String name) {
        if (!map.containsKey(name)) {
            return 0;
        }

        return map.get(name);
    }

    private void increaseValue(Map<String, Integer> map, String name, int value) {
        map.put(name, getValue(map, name) + value);
    }

}