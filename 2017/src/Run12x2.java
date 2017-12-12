import java.util.*;
import java.util.Map.Entry;

class Run12x2 extends Base {

    public static void main(String[] args) {
        new Run12x2().run();
    }

    @Override
    String doTheThing() {
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (String line : getLines()) {
            String[] parts = line.split(" <-> ");

            map.put(Integer.valueOf(parts[0]), Util.getIntegerParts(parts[1], ", "));
        }

        Set<Integer> used = new HashSet<>();

        int result = 0;

        while (true) {
            Integer start = findNextUnused(map, used);

            if (start == null) {
                break;
            }

            Set<Integer> group = findGroup(map, start);

            used.addAll(group);

            System.out.println(group.size());

            result++;
        }

        return String.valueOf(result);
    }

    private Integer findNextUnused(Map<Integer, List<Integer>> map, Set<Integer> used) {
        for (Integer integer : map.keySet()) {
            if (!used.contains(integer)) {
                return integer;
            }
        }

        return null;
    }

    private Set<Integer> findGroup(Map<Integer, List<Integer>> map, Integer start) {
        Set<Integer> resultSet = new HashSet<>();

        resultSet.add(start);

        int lastResult;

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

        return resultSet;
    }
}