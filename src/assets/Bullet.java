package assets;
import com.sun.org.apache.regexp.internal.RE;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import platformer.Platformer;


public class Bullet {

    private Vector2f position = new Vector2f();
    private Vector2f target = new Vector2f();
    private double deltaX;
    private double deltaY;
    private int speed;

    public Bullet(Vector2f position, Vector2f target) {
        this.position = new Vector2f(position.getX(),position.getY());
        this.target = new Vector2f(target.getX(),target.getY());
        speed = 10;
        calculateDirection();
    }

    public Vector2f getPosition() {
        return position;
    }

    public void update() {
        move();
    }

    public Rectangle getShape(){
        return new Rectangle(this.position.x,this.position.y,2,2);
    }

    private void calculateDirection() {
        double rad = Math.atan2(target.getX() - position.getX(), position.getY() - target.getY());
        deltaX = Math.sin(rad) * speed;
        deltaY = -Math.cos(rad) * speed;
    }


    private void move() {
        float x = position.getX() + (float)deltaX;
        float y = position.getY() + (float)deltaY;
        position.set(x, y);
    }


}