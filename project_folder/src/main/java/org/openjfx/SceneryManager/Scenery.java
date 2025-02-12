package org.openjfx.SceneryManager;

import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.openjfx.GameComponent.Player;

import java.io.FileInputStream;

/*
 * This class is responsible for creating the background middleground foreground etc.
 * Then updating them regularly so that they fit with the game.
 */
public class Scenery {
    Pane gameRoot;
    double width; // Games width/resolution
    double height; // Games height/resolution
    Hud hud;
    BackGround backGround; // background of the game
    MiddleGround middleGround; // middleground of the game
    ForeGround foreGround; // foreground of the game
    double speed;

    public Scenery(Pane gameRoot, double width, double height, double speed) {
        this.width = width;
        this.height = height;
        this.gameRoot = gameRoot;
        this.speed = speed;
    }

    /*
     * Create content for scenery
     */
    public void createContent() {
        backGround = new BackGround(width, height, "background", gameRoot);
        middleGround = new MiddleGround(width, height, "middleground", gameRoot, speed);
        foreGround = new ForeGround(width, height, "foreground", gameRoot);
        hud = new Hud(width, height, "hud", gameRoot);
    }

    /*
     * Update game
     */
    public void update(BooleanProperty[] keyInputs, Player player, int fps, double speed) {
        //Update all elements of scenery
        backGround.update(keyInputs[1].get(), player);
        hud.update(speed, fps, player.getScore());
        hud.displaySkills(player.getAbilities());
        middleGround.update(keyInputs[1].get(), keyInputs[3].get(), player, speed);
        foreGround.update(keyInputs[1].get(), player);
    }

    public void slideScenery(boolean toLeft, double slidingSpeed) {
        middleGround.slide(toLeft, slidingSpeed);
        hud.slide(toLeft, slidingSpeed);
    }

    /*
     * Inserts image in url to rectangle if fill is true
     * else open the image and return it
     */
    public ImagePattern insertImage(Rectangle button, String url, boolean fill) {
        ImagePattern imagePattern;
        try {
            // set background image
            FileInputStream inputstream = new FileInputStream(url);
            Image image = new Image(inputstream);
            imagePattern = new ImagePattern(image);
            if (fill)
                button.setFill(imagePattern);
            inputstream.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
        return imagePattern;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}
