package App;

import java.util.Random;

public class Losowanie {
    private static final Random random = new Random();

    /**
     * @param dolna the lower bound of the range, inclusive
     * @param gorna the upper bound of the range, inclusive
     * @return a random integer within the specified range
     */
    public static int losuj(int dolna, int gorna) {
        if (dolna > gorna) {
            return dolna;
        }
        return dolna + random.nextInt(gorna - dolna + 1);
    }
}
