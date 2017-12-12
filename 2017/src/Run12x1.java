import java.util.*;
import java.util.Map.Entry;

class Run12x1 extends Base {

    public static void main(String[] args) {
        new Run12x1().run();
    }

    @Override
    String doTheThing() throws Exception {
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (String line : getLines()) {
            String[] parts = line.split(" <-> ");

            map.put(Integer.valueOf(parts[0]), Util.getIntegerParts(parts[1], ", "));
        }

        Set<Integer> resultSet = new HashSet<>();

        resultSet.add(0);

        int lastResult = 0;

        do {
            lastResult = resultSet.size();

            for (Entry<Integer, List<Integer>> entry : map.entrySet()) {
                boolean add = false;
                if (resultSet.contains(entry.getKey())) {
                    add = true;
                } else {
                    for (Integer v : entry.getValue()) {
                        if (resultSet.contains(v)) {
                            add = true;
                        }
                    }
                }

                if (add) {
                    resultSet.add(entry.getKey());
                    resultSet.addAll(entry.getValue());
                }
            }
        } while (resultSet.size() != lastResult);

        return String.valueOf(resultSet.size());
    }
}