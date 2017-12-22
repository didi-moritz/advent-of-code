import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Run21x1 extends Base {

    public static void main(String[] args) {
        new Run21x1().run();
    }

    private Map<Integer, Boolean[][]> rules;

    private Boolean[][] image;

    @Override
    String doTheThing() throws Exception {
        rules = new HashMap<>();

        image = parseBlock(".#./..#/###");
        int size = image.length;

        for (String l : getLines()) {
            parseLine(l);
        }

        return "-1";
    }

    private void parseLine(String l) {
        List<String> parts  = Util.getParts(l);
        Boolean[][] to = parseBlock(parts.get(2));
        Boolean[][] from = parseBlock(parts.get(0));

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    addRule(from, to);
                    from = flipHorizontal(from);
                }
                from = flipVertical(from);
            }
            from = flipDiagonal(from);
        }
    }

    private Boolean[][] flipDiagonal(Boolean[][] block) {
        int size = block.length;

        Boolean[][] result = new Boolean[size][size];

        for (int x = 0; x < block.length; x++) {
            Boolean[] l = block[x];
            for (int y = 0; y < l.length; y++) {
                result[x][y] = block[y][x];
            }
        }

        return result;
    }

    private Boolean[][] flipHorizontal(Boolean[][] block) {
        int size = block.length;

        Boolean[][] result = new Boolean[size][size];

        for (int x = 0; x < block.length; x++) {
            Boolean[] l = block[x];
            for (int y = 0; y < l.length; y++) {
                result[x][y] = block[size - x - 1][y];
            }
        }

        return result;
    }

    private Boolean[][] flipVertical(Boolean[][] block) {
        int size = block.length;

        Boolean[][] result = new Boolean[size][size];

        for (int x = 0; x < block.length; x++) {
            Boolean[] l = block[x];
            for (int y = 0; y < l.length; y++) {
                result[x][y] = block[x][size - y - 1];
            }
        }

        return result;
    }

    private void addRule(Boolean[][] to, Boolean[][] from) {
        String stringForm = getStringForm(to);
        rules.put(stringForm.hashCode(), from);
    }

    private String getStringForm(Boolean[][] block) {
        return Arrays.stream(block).flatMap(b -> Arrays.stream(b)).map(c -> c ? "." : " ").collect(Collectors.joining());
    }


    private Boolean[][] parseBlock(String s) {
        List<String> parts = Util.getParts(s, "/");

        Boolean[][] block = new Boolean[parts.get(0).length()][parts.size()];

        for (int y = 0; y < parts.size(); y++) {
            String part = parts.get(y);
            for (int x = 0; x < part.length(); x++) {
                block[x][y] = part.charAt(x) == '#';
            }
        }

        return block;
    }
}
