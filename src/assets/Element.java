package assets;

import platformer.ShapeType;

/**
 * Created by X on 9/8/2017.
 */
public class Element {

    private int subImageX;
    private int subImageY;

    private int positionX;
    private int positionY;

    ShapeType shapeType;

    public Element(int positionX, int positionY, int subImageX, int subImageY) {
        this.subImageX = subImageX;
        this.subImageY = subImageY;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Element(int positionX, int positionY, int subImageX, int subImageY, ShapeType shapeType) {
        this.subImageX = subImageX;
        this.subImageY = subImageY;
        this.positionX = positionX;
        this.positionY = positionY;
        this.shapeType = shapeType;
    }

    public int getSubImageX() {
        return subImageX;
    }

    public int getSubImageY() {
        return subImageY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Element element = (Element) o;
//
//        return !(positionX != element.positionX && positionY == element.positionY);
//    }

//    @Override
//    public int hashCode() {
//        int result = positionX;
//        result = 31 * result + positionY;
//        return result;
//    }

    @Override
    public String toString() {
        return subImageX +
                "," + subImageY +
                "," + positionX +
                "," + positionY +
                "," + shapeType;
    }
}
