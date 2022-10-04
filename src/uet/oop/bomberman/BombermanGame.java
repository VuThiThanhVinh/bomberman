package uet.oop.bomberman;

import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.Map;
import uet.oop.bomberman.sound.Sound;

public class BombermanGame extends Application {

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

    Entity bomberman = new Bomber(Sprite.x_bomber, Sprite.y_bomber, Sprite.player_right.getFxImage());
    Entity nextBomber = new Bomber(Sprite.x_bomber, Sprite.y_bomber, Sprite.player_right.getFxImage());
    Entity object;
    Entity itemObject;
    protected static List<String> map = new Map(1).getMap();
    public static final int WIDTH = map.get(0).length();
    public static final int HEIGHT = map.size();

    private static int speed = 4; // toc do cua nhan vat

    @Override
    public void start(Stage stage) {
        //Sound.play("soundtrack");
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMap();
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            // System.out.println(bomberman.getX() + " " + bomberman.getY());

            if (key.getCode() == KeyCode.DOWN && nextBomber.getY() <= (HEIGHT - 2) * Sprite.SCALED_SIZE) {
                bomberman.setImg(Sprite.player_down.getFxImage());
                if (bomberman.getY() < (HEIGHT - 2) * Sprite.SCALED_SIZE)
                    nextBomber.setY(bomberman.getY() + Sprite.SCALED_SIZE / speed);
                if (checkCollision(nextBomber, stillObjects)){
                    bomberman.setY(nextBomber.getY());
                    if(IndexOfItem(bomberman,stillObjects) != 0) {
                        Sound.play("Item");
                        useItemFunction(stillObjects,IndexOfItem(bomberman,stillObjects));
                         object = new Grass(bomberman.getX()/Sprite.SCALED_SIZE, bomberman.getY()/Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                        stillObjects.set(IndexOfItem(bomberman,stillObjects),object);


                    }
                }

                else
                    nextBomber.setY(bomberman.getY());
                bomberman.setInput("DOWN");
            }
            if (key.getCode() == KeyCode.UP && nextBomber.getY() >= 1 * Sprite.SCALED_SIZE) {

                bomberman.setImg(Sprite.player_up.getFxImage());
                if (bomberman.getY() > 1 * Sprite.SCALED_SIZE)
                    nextBomber.setY(bomberman.getY() - Sprite.SCALED_SIZE / speed);
                if (checkCollision(nextBomber, stillObjects)){
                    bomberman.setY(nextBomber.getY());
                    if(IndexOfItem(bomberman,stillObjects) != 0) {
                        Sound.play("Item");
                        useItemFunction(stillObjects,IndexOfItem(bomberman,stillObjects));
                        object = new Grass(bomberman.getX()/Sprite.SCALED_SIZE, bomberman.getY()/Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                        stillObjects.set(IndexOfItem(bomberman,stillObjects),object);
                    }
                }

                else
                    nextBomber.setY(bomberman.getY());
                bomberman.setInput("UP");
            }
            if (key.getCode() == KeyCode.RIGHT & nextBomber.getX() <= (WIDTH - 2) * Sprite.SCALED_SIZE) {
                bomberman.setImg(Sprite.player_right.getFxImage());
                if (bomberman.getX() < (WIDTH - 2) * Sprite.SCALED_SIZE)
                    nextBomber.setX(bomberman.getX() + Sprite.SCALED_SIZE / speed);
                if (checkCollision(nextBomber, stillObjects)){
                    bomberman.setX(nextBomber.getX());
                    if(IndexOfItem(bomberman,stillObjects) != 0) {
                        Sound.play("Item");
                        useItemFunction(stillObjects,IndexOfItem(bomberman,stillObjects));
                        object = new Grass(bomberman.getX()/Sprite.SCALED_SIZE, bomberman.getY()/Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                        stillObjects.set(IndexOfItem(bomberman,stillObjects),object);
                    }
                }

                else
                    nextBomber.setX(bomberman.getX());
                bomberman.setInput("RIGHT");
            }
            if (key.getCode() == KeyCode.LEFT && nextBomber.getX() >= 1 * Sprite.SCALED_SIZE) {
                bomberman.setImg(Sprite.player_left.getFxImage());
                if (bomberman.getX() > 1 * Sprite.SCALED_SIZE)
                    nextBomber.setX(bomberman.getX() - Sprite.SCALED_SIZE / speed);
                if (checkCollision(nextBomber, stillObjects)){
                    bomberman.setX(nextBomber.getX());
                    if(IndexOfItem(bomberman,stillObjects) != 0) {
                        Sound.play("Item");
                        useItemFunction(stillObjects,IndexOfItem(bomberman,stillObjects));
                        object = new Grass(bomberman.getX()/Sprite.SCALED_SIZE, bomberman.getY()/Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                        stillObjects.set(IndexOfItem(bomberman,stillObjects),object);
                    }
                }

                else
                    nextBomber.setX(bomberman.getX());
                bomberman.setInput("LEFT");
            }

        });

    }

    public boolean checkCollision(Entity bomber, List<Entity> stillObjects) {
        Rectangle Obj1 = new Rectangle(bomber.getX(), bomber.getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        for (int i = 0; i < stillObjects.size(); i++) {
            Rectangle Obj2 = new Rectangle(stillObjects.get(i).getX(), stillObjects.get(i).getY(),
                    Sprite.SCALED_SIZE,
                    Sprite.SCALED_SIZE);
            if (Obj1.intersects(Obj2) && !(stillObjects.get(i) instanceof Grass) && !(stillObjects.get(i) instanceof Item)) {
//                System.out.println("va cham");
//                Sound.play("engineer_no01");
                return false;
            }
        }
        return true;
    }
    public void useItemFunction(List<Entity>stillObjects,int index) {
        if(stillObjects.get(index) instanceof Item_speed) speed /= 2;
        System.out.println(speed);

    }
    public int IndexOfItem(Entity bomber, List<Entity> stillObjects) {
        int index = 0;
        for(int i = 0; i < stillObjects.size(); i++) {
            if(bomber.getX() == stillObjects.get(i).getX() && bomber.getY() == stillObjects.get(i).getY()
            && (stillObjects.get(i) instanceof Item)) {
                index = i;
                break;
            }
        }
        return index;
    }


    public void createMap() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {

                if (map.get(i).charAt(j) == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());

                } else if (map.get(i).charAt(j) == '*') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                } else if (map.get(i).charAt(j) == 'b') {
                    object = new Item_bombs(j, i, Sprite.powerup_bombs.getFxImage());
                } else if (map.get(i).charAt(j) == 'f') {
                    object = new Item_flames(j, i, Sprite.powerup_flames.getFxImage());
                } else if (map.get(i).charAt(j) == 's') {
                    object = new Item_speed(j, i, Sprite.powerup_speed.getFxImage());
                } else {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);

            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
        bomberman.update();

    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        bomberman.render(gc);

    }
}
