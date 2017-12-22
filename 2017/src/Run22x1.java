import java.util.List;
import java.util.stream.Collectors;

public class Run22x1 extends Base {

    public static void main(String[] args) {
        new Run22x1().run();
    }

    private boolean[][] grid;

    private static final char INFECTED = '#';
    private static final char CLEAN = '.';

    private enum Direction {
        N(0),
        E(1),
        S(2),
        W(3);

        int id;

        Direction(int id) {
            this.id = id;
        }

        int getXInc() {
            switch (this) {
                case E:
                    return 1;
                case W:
                    return -1;
                default:
                    return 0;
            }
        }

        int getYInc() {
            switch (this) {
                case N:
                    return -1;
                case S:
                    return 1;
                default:
                    return 0;
            }
        }

        Direction toRight() {
            return byId((id + 1) % 4);
        }

        Direction toLeft() {
            return byId((id - 1 + 4) % 4);
        }

        static Direction byId(int id) {
            for (Direction direction : Direction.values()) {
                if (direction.id == id) {
                    return direction;
                }
            }

            return null;
        }
    }

    @Override
    String doTheThing() {
        Boolean[][] startGrid = parseBlock(getLines().stream().collect(Collectors.joining("/")));

        int size = 2000;
        int offset = size / 2;

        grid = new boolean[2000][2000];

        int midX = startGrid[0].length / 2;
        int midY = startGrid.length / 2;

        for (int x = 0; x < startGrid[0].length; x++) {
            for (int y = 0; y < startGrid.length; y++) {
                grid[x - midX + offset][y - midY + offset] = startGrid[x][y];
            }
        }

        int posX = offset;
        int posY = offset;

        int result = 0;
        Direction dir = Direction.N;

        for (int i = 0; i < 10000; i++) {
            if (grid[posX][posY]) {
                dir = dir.toRight();
            } else {
                dir = dir.toLeft();
                result++;
            }

            grid[posX][posY] = !grid[posX][posY];

            posX += dir.getXInc();
            posY += dir.getYInc();
        }

        return String.valueOf(result);
    }

    private Boolean[][] parseBlock(String s) {
        List<String> parts = Util.getParts(s, "/");

        Boolean[][] block = new Boolean[parts.get(0).length()][parts.size()];

        for (int y = 0; y < parts.size(); y++) {
            String part = parts.get(y);
            for (int x = 0; x < part.length(); x++) {
                block[x][y] = part.charAt(x) == INFECTED;
            }
        }

        return block;
    }
}
