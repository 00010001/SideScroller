package utils;

import assets.Bullet;
import assets.Element;
import assets.Camera;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import platformer.Platformer;
import platformer.Player;
import platformer.StaticLevel;

import java.util.List;
import java.util.Set;

/**
 * Created by X on 9/9/2017.
 */
public class Renderer {

    private static Camera camera = Camera.getInstance();

    public static void renderElementsFromList(List<Element> list, SpriteSheet sheet, Camera camera) {
        for (Element element : list) {
            Image img = sheet.getSubImage(element.getSubImageX(), element.getSubImageY());
            img.draw(element.getPositionX() - camera.getPos().x, element.getPositionY() - camera.getPos().y);
        }
    }

    public static void drawBackground(GameContainer gc, Graphics g) {
        Color bgColor = new Color(28, 20, 19);
        g.setColor(bgColor);
        g.fill(new Rectangle(0, 0, gc.getWidth(), gc.getHeight()));
    }

    public static void renderBullets(Graphics g) {
        for (Bullet bullet : Platformer.bulletList) {
            g.setColor(Color.yellow);
            g.fillRect(bullet.getPosition().getX() - camera.getPos().x, bullet.getPosition().getY() - camera.getPos().y, 5, 2);
        }
    }

    public static void renderCameraInfo(Graphics g) {
        g.drawString("Camera pos: " + camera.getPos().x + ", " + camera.getPos().y, 100, 10);
    }

    public static void renderPlayer(Graphics g) {
        g.setColor(Color.red);
        g.draw(new Rectangle(Player.getInstance().getShapeX() - camera.getPos().x, Player.getInstance().getShapeY() - camera.getPos().y, 48, 48));
    }

    public static void renderStaticLevel(Graphics g) {
        g.setColor(Color.green);

        for (Shape element : StaticLevel.getInstance().getShapeSet()) {

            if (element.getClass().getSimpleName().equals("Polygon")) {
                Polygon p = new Polygon(element.getPoints());
                p.setX(p.getX() - camera.getPos().x);
                p.setY(p.getY() - camera.getPos().y);
                g.draw(p);
            } else {
                Rectangle r = new Rectangle(element.getX(), element.getY(), element.getWidth(), element.getHeight());
                r.setX(r.getX() - camera.getPos().x);
                r.setY(r.getY() - camera.getPos().y);
                g.draw(r);
            }

        }

    }
}
