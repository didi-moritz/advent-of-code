import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Run21x2 extends Base {

    public static void main(String[] args) {
        new Run21x2().run();
    }

    private Map<Integer, Boolean[][]> rules;

    private Boolean[][] image;

    private static final char VISIBLE_DOT = '#';
    private static final char INVISIBLE_DOT = '.';

    @Override
    String doTheThing() {
        rules = new HashMap<>();

        image = parseBlock(".#./..#/###");

        for (String l : getLines()) {
            parseLine(l);
        }

        for (int i = 0; i < 18; i++) {
            int size = image.length;
            int blockSize = size % 2 == 0 ? 2 : 3;
            int newBlockSize = blockSize + 1;

            int newSize = size * newBlockSize / blockSize;

            Boolean[][] newImage = new Boolean[newSize][newSize];

            for (int j = 0; j < size / blockSize; j++) {
                for (int k = 0; k < size / blockSize; k++) {
                    int hash = getStringForm(j, k, blockSize).hashCode();
                    addBlock(j, k, newBlockSize, newImage, rules.get(hash));
                }
            }

            image = newImage;

            System.out.println("Iteration " + (i + 1) + " finished");
        }

        return String.valueOf(Arrays.stream(image)
                .flatMap(Arrays::stream)
                .filter(Boolean::booleanValue)
                .count());
    }

    private void addBlock(int j, int k, int newBlockSize, Boolean[][] newImage, Boolean[][] block) {
        for (int y = 0; y < newBlockSize; y++) {
            for (int x = 0; x < newBlockSize; x++) {
                newImage[j * newBlockSize + x][k * newBlockSize + y] = block[x][y];
            }
        }
    }

    private String getStringForm(int j, int k, int blockSize) {
        StringBuilder sb = new StringBuilder();
        for (int y = k * blockSize; y < (k + 1) * blockSize; y++) {
            for (int x = j * blockSize; x < (j + 1) * blockSize; x++) {
                sb.append(image[x][y] ? VISIBLE_DOT : INVISIBLE_DOT);
            }
        }
        return sb.toString();
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
            System.arraycopy(block[size - x - 1], 0, result[x], 0, l.length);
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

    private void addRule(Boolean[][] from, Boolean[][] to) {
        rules.put(getHash(from), to);
    }

    private int getHash(Boolean[][] block) {
        return getStringForm(block).hashCode();
    }

    private String getStringForm(Boolean[][] block) {
        return Arrays.stream(block)
                .flatMap(Arrays::stream)
                .map(c -> String.valueOf(c ? VISIBLE_DOT : INVISIBLE_DOT))
                .collect(Collectors.joining());
    }


    private Boolean[][] parseBlock(String s) {
        List<String> parts = Util.getParts(s, "/");

        Boolean[][] block = new Boolean[parts.get(0).length()][parts.size()];

        for (int y = 0; y < parts.size(); y++) {
            String part = parts.get(y);
            for (int x = 0; x < part.length(); x++) {
                block[x][y] = part.charAt(x) == VISIBLE_DOT;
            }
        }

        return block;
    }
}
