import bagel.Image;

public class Actor {
    private int x;
    private int y;
    private Image image;
    public final String TYPE;

    /* constructor to initialize value */
    public Actor(int x, int y, String fileName, String type) {
        this.x = x;
        this.y = y;
        image = new Image(fileName);
        this.TYPE = type;
    }

    /* getter function of x axis */
    public int getX() {
        return x;
    }

    /* getter function of y axis */
    public int getY() {
        return y;
    }

    /* move the actors backwards per pixel */
    public void shift() {
        x -= SpaceBunnies.PER_PIXEL;
    }

    /* render the space bunnies */
    public void render() {
        image.draw(x, y);
    }
}
