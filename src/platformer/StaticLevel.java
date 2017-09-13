package platformer;

import assets.Camera;
import assets.Element;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.w3c.dom.css.Rect;
import sun.security.provider.SHA;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StaticLevel {
    private Set<Shape> shapeSet;
    private static StaticLevel instance;

    private StaticLevel() {}

    public static StaticLevel getInstance(){
        if(instance == null){
            instance = new StaticLevel();
        }
        return instance;
    }

    public Set<Shape> getShapeSet() {
        return shapeSet;
    }

    public void init(List<Element> list) throws SlickException {

        shapeSet = new HashSet<>();
        for (Element e : list) {
            if (e.getShapeType() != null && e.getShapeType() == ShapeType.StairsRightDown) {
                shapeSet.add(ShapeFactory.getShape(ShapeType.StairsRightDown, e.getPositionX(), e.getPositionY()));
            }
            else if (e.getShapeType() != null && e.getShapeType() == ShapeType.HighCelling) {
                shapeSet.add(ShapeFactory.getShape(ShapeType.HighCelling, e.getPositionX(), e.getPositionY()));
            }
            else {
                Rectangle rect = new Rectangle(e.getPositionX(), e.getPositionY(), 48, 48);
                shapeSet.add(rect);
            }

        }
    }





    public void update(GameContainer gc, int delta) throws SlickException {

    }


    public boolean collidesWith(Shape s) {
        for (Shape shape : shapeSet) {

            if (shape.intersects(s)) {
                return true;
            }
        }
        return false;
    }
}