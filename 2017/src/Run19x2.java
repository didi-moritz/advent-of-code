public class Run19x2 extends Base {

    public static void main(String[] args) {
        new Run19x2().run(false);
    }

    private enum Direction {
        N('|'), E('-'), S('|'), W('-');

        char symbol;

        Direction(char symbol) {
            this.symbol = symbol;
        }
    }

    private char[][] chars;
    private int width;
    private int height;

    @Override
    String doTheThing() {
        width = getLines().get(0).length();
        height = getLines().size();

        System.out.println(width);
        System.out.println(height);

        chars = new char[width][height];

        for (int y = 0; y < height; y++) {
            String line = getLines().get(y);

            for (int x = 0; x < width; x++) {
                chars[x][y] = line.charAt(x);
            }
        }

        int result = 1;

        int x = 0;
        int y = 0;
        Direction dir = Direction.S;

        for (int i = 0; i < width; i++) {
            if (chars[i][y] == '|') {
                x = i;
                break;
            }
        }

        do {
            switch (dir) {
                case N:
                    y--;
                    break;
                case S:
                    y++;
                    break;
                case E:
                    x--;
                    break;
                case W:
                    x++;
                    break;
            }

            if (!pathContinues(x, y)) {
                break;
            }

            result++;

            char c = chars[x][y];

            if (c == '+') {
                if (dir.symbol == '|') {
                    if (pathContinues(x + 1, y)) {
                        dir = Direction.W;
                    } else if (pathContinues(x - 1, y)) {
                        dir = Direction.E;
                    } else {
                        break;
                    }
                } else {
                    if (pathContinues(x, y + 1)) {
                        dir = Direction.S;
                    } else if (pathContinues(x, y - 1)) {
                        dir = Direction.N;
                    } else {
                        break;
                    }
                }
            }
        } while (true);

        return String.valueOf(result);
    }

    private boolean pathContinues(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height && chars[x][y] != ' ';
    }
}
