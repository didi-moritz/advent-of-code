import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Run20x2 extends Base {

    public static void main(String[] args) {
        new Run20x2().run();
    }

    private class Coords {
        int x;
        int y;
        int z;

        public Coords(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private class Particle {
        Coords p;
        Coords v;
        Coords a;

        public Particle(Coords p, Coords v, Coords a) {
            this.p = p;
            this.v = v;
            this.a = a;
        }
    }

    private Map<Integer, Particle> particles;

    @Override
    String doTheThing() throws Exception {
        particles = new HashMap<>();

        int result = 0;

        for (int i = 0; i < getLines().size(); i++) {
            String line = getLines().get(i);
            List<String> parts = Util.getParts(line);

            particles.put(i, new Particle(
                    getCoords(parts.get(0)),
                    getCoords(parts.get(1)),
                    getCoords(parts.get(2))
            ));
        }

        while (!particles.isEmpty()) {
            moveAllParticles();

            List<Integer> collisions = getCollitions();
        }


        return "-1";
    }

    private List<Integer> getCollitions() {
        List<Integer> collisions = new ArrayList<>();
        return collisions;
    }

    private void moveAllParticles() {
        for (Particle particle : particles.values()) {
            particle.v.x += particle.a.x;
            particle.v.y += particle.a.y;
            particle.v.z += particle.a.z;

            particle.p.x += particle.v.x;
            particle.p.y += particle.v.y;
            particle.p.z += particle.v.z;
        }
    }

    private Coords getCoords(String s) {
        s = s.substring(s.indexOf('<') + 1, s.length() - 1);

        List<Integer> v = Util.getIntegerParts(s, ",");
        return new Coords(v.get(0), v.get(1), v.get(2));
    }
}
