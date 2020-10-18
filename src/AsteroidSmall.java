import java.util.Random;

public class AsteroidSmall extends Actor{
    public static final String TYPE = "AsteroidSmall";

    /* constructor to initialize value */
    public AsteroidSmall(int x, int y, String fileName) {
        super(x, y, fileName, TYPE);
    }

    /* randomize which asteroid is drawn */
    public static String typeFileSmall() {
        Random rand = new Random();
        int numAsteroid = rand.nextInt(3);
        String fileName;
        switch (numAsteroid) {
            case (0):
                fileName = "res/image/asteroid-small1.png";
            case (1):
                fileName = "res/image/asteroid-small2.png";
            default:
                fileName = "res/image/asteroid-small3.png";
        }
        return fileName;
    }
}
