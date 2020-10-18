import bagel.Image;

public class Ship {
    private static final String THREE_LIFE_FILE = "res/image/3-space-bunnies.png";
    private static final String TWO_LIFE_FILE = "res/image/2-space-bunnies.png";
    private static final String ONE_LIFE_FILE = "res/image/1-space-bunnies.png";
    private static final String ZERO_LIFE_FILE = "res/image/0-space-bunnies.png";
    private static final int THREE_LIFE= 3;
    private static final int TWO_LIFE = 2;
    private static final int ONE_LIFE = 1;
    private int condition;
    private Image image;
    private int x;
    private int y;

    /* constructor to initialize value */
    public Ship() {
        x = 1024;
        y = 348;
        image = new Image(THREE_LIFE_FILE);
        condition = THREE_LIFE;
    }

    /* getter function of x axis */
    public int getX() {
        return x;
    }

    /* getter function of y axis */
    public int getY() {
        return y;
    }

    /* check if the ship hit an asteroid */
    public Boolean checkSurrounding(Ship ship, Actor asteroid, int range) {
        if ((ship.getY() >= asteroid.getY()-range) && (ship.getY() <= asteroid.getY()+range)
        && (ship.getX() >= asteroid.getX()-range) && (ship.getX() <= asteroid.getX()+range)) {
            return true;
        } else {
            return false;
        }
    }

    /* when crash bunny ejected one out */
    public void crash() {
        condition = condition-1;
        if (condition == TWO_LIFE) {
            image = new Image(TWO_LIFE_FILE);
        } else if (condition == ONE_LIFE) {
            image = new Image(ONE_LIFE_FILE);
        } else {
            image = new Image(ZERO_LIFE_FILE);
        }
    }

    /* get function of condition */
    public int getCondition() {
        return condition;
    }

    /* move the space bunnies */
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    /* render the space bunnies */
    public void render() {
        image.draw(x,y);
    }
}
