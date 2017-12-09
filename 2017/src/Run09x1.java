class Run09x1 extends Base {

    public static void main(String[] args) {
        new Run09x1().run();
    }

    @Override
    String doTheThing() throws Exception {
        int result = 0;
        int level = 0;

        String input = getInput();

        boolean commentOpen = false;
        boolean skipChar = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (skipChar) {
                skipChar = false;
                continue;
            }

            if (commentOpen) {
                if (c == '>') {
                    commentOpen = false;
                }

                if (c == '!') {
                    skipChar = true;
                }
                continue;
            }

            switch (c) {
                case '{':
                    result += ++level;
                    break;
                case '}':
                    level--;
                    break;
                case '!':
                    skipChar = true;
                    break;
                case '<':
                    commentOpen = true;
                    break;
            }
        }

        return String.valueOf(result);
    }

}