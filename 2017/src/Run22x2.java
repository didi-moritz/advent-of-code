import java.util.List;
import java.util.stream.Collectors;

public class Run22x2 extends Base {

    public static void main(String[] args) {
        new Run22x2().run();
    }

    private byte[][] grid;

    private static final byte STATUS_CLEAN = 0;
    private static final byte STATUS_WEAKENED = 1;
    private static final byte STATUS_INFECTED = 2;
    private static final byte STATUS_FLAGGED = 3;

    private static final char INFECTED = '#';

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

        grid = new byte[2000][2000];

        int midX = startGrid[0].length / 2;
        int midY = startGrid.length / 2;

        for (int x = 0; x < startGrid[0].length; x++) {
            for (int y = 0; y < startGrid.length; y++) {
                grid[x - midX + offset][y - midY + offset] = startGrid[x][y] ? STATUS_INFECTED : STATUS_CLEAN;
            }
        }

        int posX = offset;
        int posY = offset;

        int result = 0;
        Direction dir = Direction.N;

        for (int i = 0; i < 10_000_000; i++) {
            switch (grid[posX][posY]) {
                case STATUS_CLEAN:
                    dir = dir.toLeft();
                    break;
                case STATUS_INFECTED:
                    dir = dir.toRight();
                    break;
                case STATUS_FLAGGED:
                    dir = dir.toLeft().toLeft();
                    break;
                case STATUS_WEAKENED:
                    result++;
                    break;
            }

            grid[posX][posY] = (byte) ((grid[posX][posY] + 1) % 4);

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
