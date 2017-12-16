import java.util.List;

public class Run16x2 extends Base {

    public static void main(String[] args) {
        new Run16x2().run();
    }

    private char[] chars;

    private List<String> commands;

    private int offset;

    @Override
    String doTheThing() {
        chars = new char[16];

        offset = 0;

        for (int i = 0; i < 16; i++) {
            chars[i] = (char) ('a' + i);
        }

        String start = getString();

        commands = getInputStrings(",");

        int iter = 0;
        for (int i = 0; i < 1_000_000_000; i++) {
            dance();

            if ((i + 1) % 1_000 == 0) {
                System.out.println((i + 1) + " done");
            }

            if (chars[pos(0)] == 'a') {
                if (getString().equals(start)) {
                    iter = i + 1;
                    break;
                }
            }
        }

        int f = (1_000_000_000 % iter);

        for (int i = 0; i < f; i++) {
            dance();
        }

        return getString();
    }

    private String getString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(chars[pos(i)]);
        }
        return sb.toString();
    }

    private void dance() {
        for (String command : commands) {
            try {
                char type = command.charAt(0);

                switch (type) {
                    case 's':
                        int i = Integer.parseInt(command.substring(1));
                        spin(i);
                        break;
                    case 'x':
                        int s = command.indexOf('/');
                        int a = Integer.parseInt(command.substring(1, s));
                        int b = Integer.parseInt(command.substring(s + 1));
                        exchange(a, b);
                        break;
                    case 'p':
                        partner(command.charAt(1), command.charAt(3));
                        break;
                }
            } catch (Exception e) {
                System.err.println("Error on command " + command);
                throw e;
            }
        }
    }

    private int findPos(char a) {
        for (int i = 0; i < chars.length; i++) {
            if (chars[pos(i)] == a) {
                return i;
            }
        }

        return -1;
    }

    private void partner(char a, char b) {
        exchange(findPos(a), findPos(b));
    }

    private void exchange(int a, int b) {
        char temp = chars[pos(a)];
        chars[pos(a)] = chars[pos(b)];
        chars[pos(b)] = temp;
    }

    private void spin(int i) {
        offset -= i;
        if (offset < 0) {
            offset += 16;
        }
    }

    private int pos(int i) {
        return (i + offset) % 16;
    }
}
