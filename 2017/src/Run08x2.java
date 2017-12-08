import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Run08x2 extends Base {

    public static void main(String[] args) {
        new Run08x2().run();
    }

    @Override
    String doTheThing() throws Exception {
        Map<String, Integer> map = new HashMap<>();

        int max = 0;

        for (String l : getLines()) {
            List<String> parts = Util.getParts(l);

            String name = parts.get(0);
            boolean increase = "inc".equals(parts.get(1));
            int value = Integer.valueOf(parts.get(2));

            String checkName = parts.get(4);
            String checkOperator = parts.get(5);
            int checkValue = Integer.valueOf(parts.get(6));

            if (check(map, checkName, checkOperator, checkValue)) {
                int newValue = increaseAndGetValue(map, name, increase ? value : -value);
                max = Math.max(max, newValue);
            }
        }

        return String.valueOf(max);
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

    private int increaseAndGetValue(Map<String, Integer> map, String name, int value) {
        int newValue = getValue(map, name) + value;
        map.put(name, newValue);
        return newValue;
    }

}