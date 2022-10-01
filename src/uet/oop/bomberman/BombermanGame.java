package uet.oop.bomberman;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();



    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
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

        Entity bomberman = new Bomber(Sprite.x_bomber, Sprite.y_bomber, Sprite.player_right.getFxImage());
        entities.add(bomberman);
//        entities.remove(entities.size() - 1);
//        Sprite.y_bomber++;
//        bomberman = new Bomber(Sprite.x_bomber, Sprite.y_bomber, Sprite.player_right.getFxImage());
//        entities.add(bomberman);
//        Entity bomberman1 = new Bomber(Sprite.x_bomber, Sprite.y_bomber+1, Sprite.player_right.getFxImage());
//        entities.add(bomberman1);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.DOWN && Sprite.y_bomber <= HEIGHT - 3 ) {

                entities.remove(entities.size() - 1);
                Sprite.y_bomber++;
                Entity newbomberman = new Bomber(Sprite.x_bomber, Sprite.y_bomber, Sprite.player_down.getFxImage());
                entities.add(newbomberman);
            }
            if(key.getCode()==KeyCode.UP && Sprite.y_bomber >= 2 ) {

                entities.remove(entities.size() - 1);
                Sprite.y_bomber--;
                Entity newbomberman = new Bomber(Sprite.x_bomber, Sprite.y_bomber, Sprite.player_up.getFxImage());
                entities.add(newbomberman);
            }
            if(key.getCode()==KeyCode.RIGHT && Sprite.x_bomber <= WIDTH - 3 ) {
                entities.remove(entities.size() - 1);
                Sprite.x_bomber++;
                Entity newbomberman = new Bomber(Sprite.x_bomber, Sprite.y_bomber, Sprite.player_right.getFxImage());
                entities.add(newbomberman);
            }
            if(key.getCode()==KeyCode.LEFT && Sprite.x_bomber >= 2 ) {

                entities.remove(entities.size() - 1);
                Sprite.x_bomber--;
                Entity newbomberman = new Bomber(Sprite.x_bomber, Sprite.y_bomber, Sprite.player_left.getFxImage());
                entities.add(newbomberman);
            }
        });
//        EventHandler<KeyEvent> keyEventsHandler = t -> {
//            if (t.getCode() == KeyCode.ENTER) {
//                entities.remove(bomberman);
//                y_bomber++;
//                entities.add(bomberman);
//            }
//        };

//            }
////            } else if (t.getCode() == KeyCode.ESCAPE) {
////                cancelEdit();
////            } else if (t.getCode() == KeyCode.TAB) {
////                commitHelper(false);
////                editNext(!t.isShiftDown());
////            }
//        }
    }

    public void createMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                }
                else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
