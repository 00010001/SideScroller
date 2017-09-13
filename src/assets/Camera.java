package assets;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import platformer.Player;

public class Camera {
    private Vector2f pos;
    private int speed = 1;
    private static Camera instance;


    private Camera() {
        pos = new Vector2f(1000, 1000);
    }

    public static Camera getInstance() {
        if(instance == null){
            instance = new Camera();
        }
        return instance;
    }

    public Vector2f getPos() {
        return pos;
    }

    public void update(GameContainer gc) {
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_A)) {
            if (pos.x > 10) {
                pos.x -= speed;
            }
        }
        if (input.isKeyDown(Input.KEY_D)) {
            pos.x += speed;
        }
        if (input.isKeyDown(Input.KEY_W)) {
            if (pos.y > 10) {
                pos.y -= speed;
            }
        }
        if (input.isKeyDown(Input.KEY_S)) {
            pos.y += speed;
        }
        if (input.isKeyPressed(Input.KEY_E)) {
            if (speed == 1) {
                speed = 5;
            } else {
                speed = 1;
            }
        }
    }



    public void followPlayer(GameContainer gc) {
        Player player = Player.getInstance();

        int offsetX = (int) player.getShapeX() - gc.getWidth()/4;
        int offsetY = (int) player.getShapeY() - gc.getHeight()/2;

        if (offsetX != pos.x) {
            if (offsetX < pos.x) {
                pos.x--;
            } else {
                pos.x++;
            }
        }
        if (offsetY != pos.y) {
            if (offsetY < pos.y) {
                pos.y--;
            } else {
                pos.y++;
            }
        }

    }
}
