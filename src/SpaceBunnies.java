import bagel.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import static java.lang.System.currentTimeMillis;

public class SpaceBunnies extends AbstractGame {
    public static final int PER_PIXEL = 64;
    private final Ship ship = new Ship();
    private long lastUpdateMills = 0;
    private Boolean wasHit = false;
    private int tick = -1;
    private final Font word = new Font("res/8-BIT-WONDER.TTF", 40);
    private final Font title = new Font("res/8-BIT-WONDER.TTF", 75);
    private final List<Actor> actorList = new ArrayList<>();

    /* initialize the game */
    public SpaceBunnies() {
        super(1024, 768, "Space Bunnies");
        loadMap();
    }

    /* run the game */
    public static void main(String[] args) {
        new SpaceBunnies().run();
    }

    /* read csv file */
    public void loadMap() {
        String mapFile = "res/map/space-bunnies.csv";

        /* try through the csv file */
        try (BufferedReader worldReader =
                     new BufferedReader(new FileReader(mapFile))) {
            String row;

            /* going through every line in the csv file */
            while((row = worldReader.readLine()) != null) {
                String[] input = row.split(",");
                String name = input[0];
                int x = Integer.parseInt(input[1]);
                int y = Integer.parseInt(input[2]);

                /* insert the input to the list */
                Actor actorInput = null;
                String file;
                switch (name) {
                    case AsteroidLarge.TYPE:
                        file = AsteroidLarge.typeFileLarge();
                        actorInput = new AsteroidLarge(x,y, file);
                        break;
                    case AsteroidSmall.TYPE:
                        file = AsteroidSmall.typeFileSmall();
                        actorInput = new AsteroidLarge(x,y, file);
                        break;
                    case Background.TYPE:
                        actorInput = new Background();
                        break;
                    case StartPlanet.TYPE:
                        actorInput = new StartPlanet();
                        break;
                    case EndPlanet.TYPE:
                        actorInput = new EndPlanet();
                        break;
                }
                actorList.add(actorInput);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    /* take input as parameter and update */
    @Override
    protected void update(Input input) {
        Boolean status = false;

        /* check input and move accordingly */
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        } else if (input.wasPressed(Keys.UP)) {
            ship.move(0, -PER_PIXEL);
        } else if (input.wasPressed(Keys.DOWN)) {
            ship.move(0, PER_PIXEL);
        }

        /* shift background and asteroid every 500 ms */
        long thisUpdateMills = currentTimeMillis();
        int tickRate = 550;
        if (thisUpdateMills - lastUpdateMills > tickRate) {

            /* move actors if haven't reach the end */
            if (actorList.get(0).getX() >= -960) {
                for (Actor actor : actorList) {
                    actor.shift();
                }
            }

            /* update the tick accordingly */
            if (tick < 13) {
                ship.move(-SpaceBunnies.PER_PIXEL, 0);
                tick++;
            } else if (tick < 14) {
                ship.move(SpaceBunnies.PER_PIXEL, 0);
                tick++;
            } else if (tick > 19) {
                Window.close();
            } else if (ship.getCondition() == 0 || (actorList.get(0).getX() <= -960)) {
                tick++;
            }

            /* check if hit asteroid */
            if (ship.getCondition() != 0) {
                for (Actor actor : actorList) {
                    if (actor instanceof AsteroidLarge) {
                        status = ship.checkSurrounding(ship, actor, PER_PIXEL);
                    } else if (actor instanceof AsteroidSmall) {
                        status = ship.checkSurrounding(ship, actor, PER_PIXEL/2);
                    } else {
                        status = false;
                    }

                    if (status) {
                        break;
                    }
                }


                /* update the status */
                if (status) {
                    if (!wasHit) {
                        ship.crash();
                        wasHit = true;
                    }
                } else {
                    if (wasHit) {
                        wasHit = false;
                    }
                }
            }
            lastUpdateMills = thisUpdateMills;
        }

        /* render images */
        for (Actor actor : actorList) {
            actor.render();
        }
        ship.render();

        /* add text according to specifications */
        if (tick<3) {
            title.drawString("Space Bunnies", 70, 380);
        } else if (tick < 6) {
            title.drawString("Space Bunnies", 70, 380);
            word.drawString("Avoid the asteroids", 200, 476);
        } else if (actorList.get(0).getX() <= -960){
            word.drawString("Mission accomplished", 150, 400);
        } else if (ship.getCondition() == 0) {
            word.drawString("Mission failed", 250, 400);
        }
    }
}
