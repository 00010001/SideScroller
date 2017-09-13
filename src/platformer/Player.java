package platformer;

import assets.Bullet;
import assets.Camera;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Player {

    private static float gravity = 0.5f;
    private static float jumpStrength = -8;
    private static float speed = 3;

    private static int interations = 15;


    private Shape shape;
    private StaticLevel level = StaticLevel.getInstance();

    private float vX = 0;
    private float vY = 0;

    private static Player instance;

    private Player() {}

    public static Player getInstance() {
        if(instance == null){
            instance = new Player();
        }
        return instance;
    }

    public float getShapeX() {
        return shape.getX();
    }

    public float getShapeY() {
        return shape.getY();
    }

    public void init(GameContainer gc) throws SlickException {
        shape = new Rectangle(1200, 1220, 48, 48);
    }



    public void update(GameContainer gc, int delta) throws SlickException {

        Camera camera = Camera.getInstance();

        if(gc.getInput().isMousePressed(0)){
            Vector2f target = new Vector2f(gc.getInput().getAbsoluteMouseX()+camera.getPos().x,gc.getInput().getAbsoluteMouseY()+camera.getPos().y);
            Vector2f origin = new Vector2f(shape.getX()+10,shape.getY()+10);
            System.out.println("Mouse: X: "+ target.getX() + "Y:" + target.getY());
            System.out.println("Player: X: "+ origin.getX() + "Y:" + origin.getY());

            Platformer.bulletList.add(new Bullet(origin,target));
        }



        // Y acceleration
        vY += gravity;
        if (gc.getInput().isKeyDown(Input.KEY_UP)) {
            shape.setY(shape.getY() + 0.5f);
            if (level.collidesWith(shape)) {
              //  System.out.print("jump");
                vY = jumpStrength;
            }
            shape.setY(shape.getY() - 0.5f);
        }
        // Y Movement-Collisions
        float vYtemp = vY / interations;
        for (int i = 0; i < interations; i++) {
            shape.setY(shape.getY() + vYtemp);
            if (level.collidesWith(shape)) {
                shape.setY(shape.getY() - vYtemp);
                vY = 0;
            }
        }

        // X acceleration
        if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
            vX = -speed;
        } else if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
            vX = speed;
        } else {
            vX = 0;
        }

        // X Movement-Collisions
        float vXtemp = vX / interations;
        for (int i = 0; i < interations; i++) {
            shape.setX(shape.getX() + vXtemp);
            if (level.collidesWith(shape)) {
                shape.setX(shape.getX() - vXtemp);
                vX = 0;
            }
        }
    }
}