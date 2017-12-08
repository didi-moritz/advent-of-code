class Run05x2 extends Base {

    public static void main(String[] args) {
        new Run05x2().run();
    }

    @Override
    String doTheThing() {
        int result = 0;

        for (String l : getLines()) {
            boolean hasDouble = false;
            boolean hasShadow = false;
            for (int i = 0; (!hasDouble || !hasShadow) && i < l.length() - 1; i++) {
                String a = l.substring(i, i + 2);
                if (l.indexOf(a, i + 2) > -1) {
                    hasDouble = true;
                }

                if (i < l.length() - 2) {
                    if (l.charAt(i) == l.charAt(i + 2)) {
                        hasShadow = true;
                    }
                }
            }

            if (hasDouble && hasShadow) {
                System.out.println(l);
                result++;
            }
        }

        return String.valueOf(result);
    }
}