package platformer;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import static platformer.ShapeType.StairsRightDown;


public class ShapeFactory {
    public static Shape getShape(ShapeType shapeType, int offsetX, int offsetY){
        switch (shapeType){
            case StairsRightDown:
                return getStairsRightDown(offsetX,offsetY);
            case HighCelling:
                return new Rectangle(offsetX,offsetY,48,14);
        }
        return null;
    }

    private static Polygon getStairsRightDown(int offsetX, int offsetY){
        float[] polygonPoints = new float[]{
                0 + offsetX, 0 + offsetY,
                11 + offsetX, 0 + offsetY,
                11 + offsetX, 9 + offsetY,
                21 + offsetX, 9 + offsetY,
                21 + offsetX, 19 + offsetY,
                30 + offsetX, 19 + offsetY,
                30 + offsetX, 29 + offsetY,
                39 + offsetX, 29 + offsetY,
                39 + offsetX, 38 + offsetY,
                48 + offsetX, 38 + offsetY,
                48 + offsetX, 48 + offsetY,
                0 + offsetX, 48 + offsetY
        };
        return new Polygon(polygonPoints);
    }


}
