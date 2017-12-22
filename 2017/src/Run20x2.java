import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Run20x2 extends Base {

    public static void main(String[] args) {
        new Run20x2().run();
    }

    private class Coords {
        long x;
        long y;
        long z;

        Coords(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private class Particle {
        Coords p;
        Coords v;
        Coords a;

        boolean markDelete = false;

        Particle(Coords p, Coords v, Coords a) {
            this.p = p;
            this.v = v;
            this.a = a;
        }

        boolean samePosition(Particle o) {
            return this.p.x == o.p.x && this.p.y == o.p.y && this.p.z == o.p.z;
        }
    }

    private List<Particle> particles;
    private int result;

    @Override
    String doTheThing() {
        particles = new ArrayList<>();

        for (int i = 0; i < getLines().size(); i++) {
            String line = getLines().get(i);
            List<String> parts = Util.getParts(line);

            particles.add(new Particle(
                    getCoords(parts.get(0)),
                    getCoords(parts.get(1)),
                    getCoords(parts.get(2))
            ));
        }

        result = particles.size();

        for (int i = 0; i < 10_000 && !particles.isEmpty(); i++) {
            moveAllParticles();

            removeLostParticles();

            findCollisions();

            removeCollisions();

            int j = i + 1;
            if (j % 100 == 0) {
                System.out.println(j + " -> " + result);
            }
        }


        return String.valueOf(result);
    }

    private void removeCollisions() {
        Iterator<Particle> iterator = particles.iterator();

        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            if (particle.markDelete) {
                iterator.remove();
                result--;
            }
        }
    }

    private void removeLostParticles() {
        particles.removeIf(particle ->
                Math.abs(particle.v.x) > Integer.MAX_VALUE ||
                Math.abs(particle.v.y) > Integer.MAX_VALUE ||
                Math.abs(particle.v.z) > Integer.MAX_VALUE);
    }

    private void findCollisions() {
        for (int i = 0; i < particles.size(); i++) {
            Particle p1 = particles.get(i);

            if (p1.markDelete) {
                continue;
            }

            for (int j =i + 1; j < particles.size(); j++) {
                Particle p2 = particles.get(j);

                if (p1.samePosition(p2)) {
                    p1.markDelete = true;
                    p2.markDelete = true;
                }
            }
        }
    }

    private void moveAllParticles() {
        for (Particle particle : particles) {
            particle.v.x += particle.a.x;
            particle.v.y += particle.a.y;
            particle.v.z += particle.a.z;

            particle.p.x += particle.v.x;
            particle.p.y += particle.v.y;
            particle.p.z += particle.v.z;
        }
    }

    private Coords getCoords(String s) {
        s = s.substring(s.indexOf('<') + 1, s.indexOf('>'));

        List<Integer> v = Util.getIntegerParts(s, ",");
        return new Coords(v.get(0), v.get(1), v.get(2));
    }
}
